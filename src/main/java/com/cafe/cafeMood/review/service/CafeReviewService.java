package com.cafe.cafeMood.review.service;

import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import com.cafe.cafeMood.aggregate.service.CafeTagAggregateService;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.review.domain.CafeReview;
import com.cafe.cafeMood.review.domain.CafeReviewTag;
import com.cafe.cafeMood.review.dto.request.CafeReviewCreateRequest;
import com.cafe.cafeMood.review.repo.CafeReviewRepository;
import com.cafe.cafeMood.review.repo.CafeReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeReviewService {

    private final CafeReviewRepository reviewRepository;
    private final CafeReviewTagRepository reviewTagRepository;
    private final CafeTagAggregateService tagAggregateService;


    @Transactional
    public Long createReview(CafeReviewCreateRequest request) {
        CafeReview review =
    }

    private CafeReview saveReview(CafeReviewCreateRequest request) {
        return reviewRepository.save(
                CafeReview.of(request.cafeId(),
                        request.userId(),
                        request.rating(),
                        request.content())
        );
    }

    private CafeReview findReview(Long reviewId) {
        return reviewRepository.findByIdAndDeletedAtIsNull(reviewId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));
    }

    private void createReviewTags(Long reviewId, Long cafeId, List<Long> tagIds){
        for (Long tagId : tagIds) {
            reviewTagRepository.save(CafeReviewTag.create(reviewId,cafeId,tagId));
        }
    }

    private void removeReviewTags(Long reviewId,List<Long> removeTagIds){
        if (removeTagIds.isEmpty()){
            return;
        }
        List<CafeReviewTag> reviewTags = reviewTagRepository.findAllByReviewId(reviewId);

        List<CafeReviewTag> targets = reviewTags.stream().filter(reviewTag -> removeTagIds.contains(reviewTag.getTagId())).toList();

        reviewTagRepository.deleteAll(targets);
    }

    private void increaseTagAggregates(Long cafeId, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            tagAggregateService.increase(cafeId,tagId);
        }
    }

    private void decreaseTagAggregates(Long cafeId, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            tagAggregateService.decrease(cafeId,tagId);
        }
    }

    private List<Long> getCurrentTagIds(Long reviewId){
        return reviewTagRepository.findAllByReviewId(reviewId).stream().map(CafeReviewTag::getTagId).distinct().toList();
    }

    private List<Long> getDistinctTagIds(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return Collections.emptyList();
        }
        return tagIds.stream().distinct().toList();
    }

    private List<Long> getTagIdsToAdd(List<Long> currentTagIds, List<Long> newTagIds) {
        Set<Long> currentTagIdSet = Set.copyOf(currentTagIds);

        return newTagIds.stream().filter(tagId -> !currentTagIdSet.remove(tagId)).toList();
    }

    private List<Long> getTagIdsToRemove(List<Long> currentTagIds, List<Long> newTagIds) {
        Set<Long> newTagIdSet = Set.copyOf(currentTagIds);

        return currentTagIds.stream().filter(tagId -> !newTagIdSet.remove(tagId)).toList();
    }
}
