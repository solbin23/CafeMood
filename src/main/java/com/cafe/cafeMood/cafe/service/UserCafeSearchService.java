package com.cafe.cafeMood.cafe.service;


import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.domain.cafe.CafeStatus;
import com.cafe.cafeMood.cafe.dto.response.CafeDetailResponse;
import com.cafe.cafeMood.cafe.dto.response.CafeSearchResponse;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.menu.domain.MenuCategory;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.menu.dto.response.TopMenuResponse;
import com.cafe.cafeMood.menu.repo.MenuRepository;
import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.repo.CafeTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCafeSearchService {

    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CafeTagRepository tagRepository;
    private final CafeTagAggregateRepository tagAggregateRepository;
    private final CafeTagAggregateRepository cafeTagAggregateRepository;

    public List<CafeSearchResponse> searchCafeMood(String mood) {
        Tag tag = tagRepository.findByName(mood).orElseThrow(() -> new BusinessException(ErrorCode.TAG_NOT_FOUND));

        List<CafeTagAggregate> aggregates = cafeTagAggregateRepository.findTopByTagIdOrderByScoreDescCountDesc(tag.getId());

        return aggregates.stream()
                .map(aggregate ->  cafeRepository.findByIdAndStatusNot(aggregate.getCafeId(), CafeStatus.DELETED).orElse(null))
                .filter(cafe -> cafe != null && cafe.getStatus().isPublish())
                .map(this::cafeSearchResponse)
                .toList();
    }

    public CafeDetailResponse cafeDetail(Long cafeId) {
        Cafe cafe = cafeRepository.findByIdAndStatusNot(cafeId, CafeStatus.DELETED).orElseThrow(() -> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        List<String> moodTags = getMoodTags(cafeId);

        Map<String, List<MenuResponse>> menus = menuRepository.findAllByCafeIdAndStatusNotOrderByCategoryAscDisplayOrderAsc(cafeId,MenuStatus.DELETED)
                .stream()
                .map(MenuResponse::of)
                .collect(Collectors.groupingBy(menu->menu.category().name()));

        return CafeDetailResponse.of(cafe, moodTags, menus);
    }

    private CafeSearchResponse cafeSearchResponse(Cafe cafe) {
        List<String> moodTags = getMoodTags(cafe.getId());

        List<TopMenuResponse> coffeeTopMenu = menuRepository.findTop3ByCafeIdAndCategoryAndStatusOrderByDisplayOrderAsc(cafe.getId(), MenuCategory.COFFEE, MenuStatus.ON_SALE)
                .stream()
                .map(TopMenuResponse::from)
                .toList();

        List<TopMenuResponse> dessertTopMenu = menuRepository.findTop3ByCafeIdAndCategoryAndStatusOrderByDisplayOrderAsc(cafe.getId(), MenuCategory.DESSERT,MenuStatus.ON_SALE)
                .stream().map(TopMenuResponse::from).toList();

        return CafeSearchResponse.of(cafe, moodTags, coffeeTopMenu, dessertTopMenu);
    }

    private List<String> getMoodTags(Long cafeId) {
        List<CafeTagAggregate> aggregates = cafeTagAggregateRepository.findAllByCafeId(cafeId);
        if (aggregates.isEmpty()) {
            return Collections.emptyList();
        }
        Map<Long, String> tagName = tagRepository.findAllById(aggregates.stream().map(CafeTagAggregate::getTagId).toList()).stream()
                .collect(Collectors.toMap(Tag::getId, Tag::getName));

        return aggregates.stream()
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .map(aggregate -> tagName.get(aggregate.getTagId()))
                .filter(name -> name !=null)
                .limit(5)
                .toList();
    }
}
