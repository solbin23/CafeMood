package com.cafe.cafeMood.aggregate.repo;

import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeTagAggregateRepository extends JpaRepository<CafeTagAggregate, Long> {

    Optional<CafeTagAggregate> findByCafeIdAndTagId(Long cafeId, Long tagId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from CafeTagAggregate a where a.cafeId = :cafeId and a.tagId = :tagId")
    Optional<CafeTagAggregate> findForUpdate(@Param("cafeId") Long cafeId, @Param("tagId") Long tagId);


    List<CafeTagAggregate> findTopByTagIdOrderByScoreDescCountDesc(Long tagId);

    List<CafeTagAggregate> findAllByCafeId(Long cafeId);
}

