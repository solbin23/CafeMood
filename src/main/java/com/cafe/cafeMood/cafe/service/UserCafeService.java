package com.cafe.cafeMood.cafe.service;


import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.dto.request.CafeSearchRequest;
import com.cafe.cafeMood.cafe.dto.response.*;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuCategory;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.menu.dto.response.TopMenuResponse;
import com.cafe.cafeMood.menu.repo.MenuRepository;
import com.cafe.cafeMood.review.domain.CafeReview;
import com.cafe.cafeMood.review.dto.response.CafeReviewResponse;
import com.cafe.cafeMood.review.repo.CafeReviewRepository;
import com.cafe.cafeMood.review.repo.CafeReviewTagSelectionRepository;
import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.dto.TagScoreSummary;
import com.cafe.cafeMood.tag.dto.TopTagResponse;
import com.cafe.cafeMood.tag.repo.CafeTagRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.MaxValidatorForNumber;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCafeService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CafeTagRepository tagRepository;
    private final CafeReviewRepository cafeReviewRepository;
    private final CafeReviewTagSelectionRepository tagSelectionRepository;
    private final CafeTagAggregateRepository cafeTagAggregateRepository;


    public List<CafeListResponse> getPublishedCafes() {
        return cafeRepository.findByStatus(CafeStatus.PUBLISHED).stream()
                .map(cafe -> new CafeListResponse(cafe.getId(),cafe.getName(),cafe.getAddress(),List.of(),cafe.getStatus())).toList();
    }

    public List<CafeSearchResponse> searchCafeMood(CafeSearchRequest request) {
        validateDuplicateTagIds(request.tagIds());
        List<CafeSearchFlat> cafes = cafeTagAggregateRepository.searchCafesByTags(request.tagIds(), PageRequest.of(0,20));

        if (cafes.isEmpty()) {
            return List.of();
        }

        List<Long> cafeIds = cafes.stream()
                .map(CafeSearchFlat::cafeId)
                .toList();

        Map<Long,List<TopTagResponse>> tagMap = getTopTags(cafeIds);
        Map<Long,List<TopMenuResponse>> coffeMap = getTopMenus(cafeIds,MenuCategory.COFFEE);
        Map<Long,List<TopMenuResponse>> dessertMap = getTopMenus(cafeIds,MenuCategory.DESSERT);

        return cafes.stream()
                .map(cafe -> new CafeSearchResponse(
                        cafe.cafeId(),
                        cafe.cafeName(),
                        cafe.address(),
                        cafe.totalScore(),
                        tagMap.getOrDefault(cafe.cafeId(), List.of()),
                        coffeMap.getOrDefault(cafe.cafeId(),List.of()),
                        dessertMap.getOrDefault(cafe.cafeId(),List.of())
                )).toList();
    }

    public CafeDetailResponse getCafeDetail(Long cafeId) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED).orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        List<TopTagResponse> topTags = cafeTagAggregateRepository.findTopTagsByCafeId(List.of(cafeId)).stream().limit(3)
                .map(tag -> new TopTagResponse(tag.tagId(),tag.tagName(),tag.score())).toList();

        List<CafeReview> reviews = cafeReviewRepository.findByCafeIdOrderByCreatedAtDesc(cafeId);
        List<Long> reviewIds = reviews.stream().map(CafeReview::getId).toList();

        Map<Long, List<String>> reviewTagNamesMap = getReviewTagNamesMap(reviewIds);
        Map<String,List<MenuResponse>> menus = menuMap(cafeId);

        List<CafeReviewResponse> reviewResponses = reviews.stream().map(review -> CafeReviewResponse.fromT(
                review,
                reviewTagNamesMap.getOrDefault(review.getId(),List.of())
        )).toList();

        return new CafeDetailResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getPhone(),
                cafe.getShortDesc(),
                menus,
                topTags,
                reviewResponses
        );
    }

    private Map<Long, List<TopTagResponse>> getTopTags(List<Long> cafeIds) {
        List<TagScoreSummary> aggregates = cafeTagAggregateRepository.findTopTagsByCafeId(cafeIds);

        return aggregates.stream()
                .collect(Collectors.groupingBy(TagScoreSummary::cafeId,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(Collectors.toList(),
                                list ->list.stream().limit(3).map(tag -> new TopTagResponse(tag.tagId(),tag.tagName(),tag.score()))

                                        .toList())));
    }


    private Map<String, List<MenuResponse>> menuMap(Long cafeId) {
        List<Menu> menuList = menuRepository.findByCafeIdOrderByCategoryAscIdAsc(cafeId);
        return menuList.stream()
                .collect(Collectors.groupingBy(
                        menu -> menu.getCategory().name(),
                        LinkedHashMap::new,
                        Collectors.mapping(MenuResponse::of,Collectors.toList())
                ));
    }

    private Map<Long,List<TopMenuResponse>> getTopMenus(List<Long> cafeIds, MenuCategory category) {
        List<Menu> menus = menuRepository.findMenusByCafeIdsAndCategory(cafeIds,category);

        return menus.stream()
                .collect(Collectors.groupingBy(
                        Menu::getCafeId,
                        LinkedHashMap::new,
                        Collectors.collectingAndThen(
                                Collectors.mapping(
                                        TopMenuResponse::from,
                                        Collectors.toList()
                                ),
                                list -> list.stream().limit(3).toList()
                        )
                ));
    }

    private void validateDuplicateTagIds(List<Long> tagIds) {
        Set<Long> uniqueTagIds = new HashSet<>(tagIds);
        if (uniqueTagIds.size() != tagIds.size()) {
            throw new BusinessException(ErrorCode.TAG_NOT_FOUND);
        }
    }

    private Map<Long,List<String>> getReviewTagNamesMap(List<Long> reviewIds) {
        if (reviewIds.isEmpty()){
            return Map.of();
        }
    Map<Long,List<Long>> reviewTagIdsMap = reviewIds.stream().collect(Collectors.toMap(
            Function.identity(),
            reviewId -> tagSelectionRepository.findByReviewId(reviewId).stream().map(selection -> selection.getTagId()).toList()
    ));
        Set<Long> allTagIds = reviewTagIdsMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        Map<Long, String> tagNameMap = tagRepository.findAllById(allTagIds).stream()
                .collect(Collectors.toMap(Tag::getId, Tag::getName));

        Map<Long, List<String>> result = new HashMap<>();
        for (Map.Entry<Long, List<Long>> entry : reviewTagIdsMap.entrySet()) {
            List<String> tagNames = entry.getValue().stream()
                    .map(tagNameMap::get)
                    .filter(Objects::nonNull)
                    .toList();
            result.put(entry.getKey(), tagNames);
        }
        return result;

    }
}
