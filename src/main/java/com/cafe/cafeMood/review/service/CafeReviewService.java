package com.cafe.cafeMood.review.service;

import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import com.cafe.cafeMood.aggregate.service.CafeTagAggregateService;
import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.review.domain.CafeReview;
import com.cafe.cafeMood.review.domain.CafeReviewTag;
import com.cafe.cafeMood.review.domain.CafeReviewTagSelection;
import com.cafe.cafeMood.review.dto.request.CafeReviewCreateRequest;
import com.cafe.cafeMood.review.dto.request.CafeReviewUpdateRequest;
import com.cafe.cafeMood.review.dto.response.CafeReviewResponse;
import com.cafe.cafeMood.review.repo.CafeReviewRepository;
import com.cafe.cafeMood.review.repo.CafeReviewTagRepository;
import com.cafe.cafeMood.review.repo.CafeReviewTagSelectionRepository;
import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.repo.CafeTagRepository;
import com.cafe.cafeMood.user.domain.User;
import com.cafe.cafeMood.user.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeReviewService {

    private final CafeReviewRepository reviewRepository;
    private final CafeReviewTagSelectionRepository tagSelectionRepository;
    private final CafeReviewTagRepository reviewTagRepository;
    private final CafeTagRepository tagRepository;
    private final CafeTagAggregateService tagAggregateService;
    private final UserRepository userRepository;
    private final CafeRepository cafeRepository;
    private final CafeTagAggregateService cafeTagAggregateService;

    public Long createReview(LoginUser loginUser, Long cafeId, CafeReviewCreateRequest request) {
        validateDuplicateTagIds(request.tagIds());

        User user = getUser(loginUser.userId());
        Cafe cafe = getPublishedCafe(cafeId);

        if (reviewRepository.existsByCafeIdAndUserId(cafeId, user.getId())) {
            throw new BusinessException(ErrorCode.ALREADY_REVIEWED_CAFE);
        }

        List<Tag> tags = validateAndGetTags(request.tagIds());

        CafeReview cafeReview = reviewRepository.save(
                CafeReview.builder()
                        .cafe(cafe)
                        .user(user)
                        .content(request.content())
                        .build()
        );

        for (Tag tag : tags) {
            tagSelectionRepository.save(CafeReviewTagSelection.of(cafeReview.getId(), tag.getId()));
            tagAggregateService.increase(cafe.getId(), tag.getId());
        }

        return cafeReview.getId();
    }



    private void updateReview(LoginUser loginUser, Long reviewId,CafeReviewUpdateRequest request) {
        validateDuplicateTagIds(request.tagIds());

        CafeReview cafeReview = reviewRepository.findByIdAndUserId(reviewId,loginUser.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));

        if (cafeReview.getCafe().getStatus() != CafeStatus.PUBLISHED) {
            throw new BusinessException(ErrorCode.INVALID_CAFE_STATE);
        }
        List<Tag> newTags = validateAndGetTags(request.tagIds());
        List<CafeReviewTagSelection> selections = tagSelectionRepository.findByReviewId(reviewId);

        Set<Long> oldTagIds = selections.stream()
                .map(CafeReviewTagSelection::getTagId)
                .collect(Collectors.toSet());

        Set<Long> newTagIds = newTags.stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        Set<Long> removedTagIds = new HashSet<>();
        removedTagIds.addAll(oldTagIds);

        Set<Long> addedTagIds = new HashSet<>();
        addedTagIds.addAll(newTagIds);

        Long cafeId = cafeReview.getCafe().getId();

        for (Long removedTagId : removedTagIds) {
            tagAggregateService.decrease(cafeId,removedTagId);
        }

        for (Long addedTagId : addedTagIds) {
            cafeTagAggregateService.increase(cafeId,addedTagId);

        }

        tagSelectionRepository.deleteByReviewId(reviewId);

        for (Tag tag : newTags) {
            tagSelectionRepository.save(CafeReviewTagSelection.of(reviewId, tag.getId()));
        }

        cafeReview.update(request.content());

    }


    public void deleteReview(LoginUser loginUser, Long reviewId) {
        CafeReview cafeReview = reviewRepository.findByIdAndUserId(reviewId, loginUser.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));

        List<CafeReviewTagSelection> selections = tagSelectionRepository.findByReviewId(reviewId);

        for (CafeReviewTagSelection selection : selections) {
            tagAggregateService.decrease(cafeReview.getCafe().getId(), selection.getTagId());
        }
        tagSelectionRepository.deleteByReviewId(reviewId);
        reviewRepository.delete(cafeReview);
    }

    private Cafe getPublishedCafe(Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId).orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        if (cafe.getStatus() != CafeStatus.PUBLISHED) {
            throw new BusinessException(ErrorCode.INVALID_CAFE_STATE);
        }
        return cafe;
    }

    private User getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private List<Tag> validateAndGetTags(List<Long> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw  new BusinessException(ErrorCode.TAG_NOT_FOUND);
        }
        Map<Long, Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

        List<Tag> orderedTags = new ArrayList<>();
        for (Long tagId : tagIds) {
            Tag tag = tagMap.get(tagId);
            if (tag == null) {
                throw new BusinessException(ErrorCode.TAG_NOT_FOUND);
            }
            if (!tag.isActive()) {
                throw new BusinessException(ErrorCode.INVALID_TAG);
            }
            orderedTags.add(tag);
        }
        return orderedTags;
    }

    private void validateDuplicateTagIds(List<Long> tagIds) {
        Set<Long> uniqueTagIds = new HashSet<>(tagIds);
        if (uniqueTagIds.size() != tagIds.size()) {
            throw new BusinessException(ErrorCode.TAG_NOT_FOUND);
        }
    }
}
