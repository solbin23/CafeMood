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
        CafeTagAggregate aggregate = findOrCreateWithLock(cafeId,tagId);
        aggregate.increase();
    }

    @Transactional
    public void decrease(Long cafeId, Long tagId) {
        CafeTagAggregate aggregate = cafeTagAggregateRepository.findForUpdate(cafeId,tagId).orElse(null);
        if (aggregate == null) {
            return;
        }
        aggregate.decrease();

    }

    private CafeTagAggregate findOrCreateWithLock(Long cafeId, Long tagId) {
        return cafeTagAggregateRepository.findForUpdate(cafeId, tagId).orElseGet(()-> createAggregate(cafeId,tagId));

    }


    private Optional<CafeTagAggregate> findWithLock(Long cafeId, Long tagId) {
        return cafeTagAggregateRepository.findForUpdate(cafeId,tagId);
    }

    private CafeTagAggregate createAggregate(Long cafeId, Long tagId) {
        try {
            CafeTagAggregate aggregate = cafeTagAggregateRepository.save(CafeTagAggregate.create(cafeId, tagId));
        return findWithLock(cafeId, tagId).orElseThrow(() -> new IllegalStateException("집계 row 생성 후 조회 실패"));
        }catch (DataIntegrityViolationException e) {
            return findWithLock(cafeId, tagId).orElseThrow(() -> new IllegalStateException("row 조회 실패"));
        }
    }
}
