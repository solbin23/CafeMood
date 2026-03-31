package com.cafe.cafeMood.cafe.service;

import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;

import com.cafe.cafeMood.cafe.dto.request.RecommendCafeRequest;
import com.cafe.cafeMood.cafe.dto.response.RecommendCafeResponse;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeRecommendationService {

    private final CafeTagAggregateRepository aggregateRepository;

    public List<RecommendCafeResponse> recommend(RecommendCafeRequest cafeRequest) {
        validateDuplicateTagIds(cafeRequest.tagIds());

        return aggregateRepository.findRecommendCafes(cafeRequest.tagIds(), PageRequest.of(0,20));
    }

    private void validateDuplicateTagIds(List<Long> tagIds) {
        Set<Long> uniqueIds = new HashSet<>(tagIds);
        if (uniqueIds.size() != tagIds.size()) {
            throw new BusinessException(ErrorCode.DUPLICATE_TAG_SELECTION);
        }
    }
}
