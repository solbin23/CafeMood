package com.cafe.cafeMood.aggregate.service;

import com.cafe.cafeMood.aggregate.repo.CafeTagAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeTagAggregateService {
    private final CafeTagAggregateRepository cafeTagAggregateRepository;

    @Transactional
    public void increase(Long cafeId, Long tagId) {

    }
}
