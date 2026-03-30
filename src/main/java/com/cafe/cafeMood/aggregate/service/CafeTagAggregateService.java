package com.cafe.cafeMood.aggregate.service;

import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeTagAggregateService {
    private final CafeTagAggregateRepository cafeTagAggregateRepository;

    @Transactional
    public void increase(Long cafeId, Long tagId) {
    int update = cafeTagAggregateRepository.incrementScore(cafeId, tagId);

    if (update == 0) {
        try {
            cafeTagAggregateRepository.saveAndFlush(new CafeTagAggregate(cafeId, tagId, 1L));
        } catch (DataIntegrityViolationException e) {
            cafeTagAggregateRepository.incrementScore(cafeId, tagId);
        }
    }
    }

    @Transactional
    public void decrease(Long cafeId, Long tagId) {
        cafeTagAggregateRepository.decrementScore(cafeId, tagId);

    }

}
