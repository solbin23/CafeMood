package com.cafe.cafeMood.cafe.repo.tag;

import com.cafe.cafeMood.cafe.domain.tag.CafeTagAggregate;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CafeTagAggregateRepository extends JpaRepository<CafeTagAggregate, CafeTagAggregate.Pk> {

    Optional<CafeTagAggregate> findByCafeIdAndTagId(Long cafeId, Long tagId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from CafeTagAggregate a where a.cafeId = :cafeId and a.tagId = :tagId")
    Optional<CafeTagAggregate> findByCafeIdAndTagIdForUpdate(Long cafeId, Long tagId);


    @Query(value = """
    SELECT * FROM cafe_tag_aggregate
    WHERE cafe_id = :cafeId
    ORDER BY total_count DESC , unique_user_count DESC, last_vote_date DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<CafeTagAggregate> findTopByCafeId(long cafeId, int limit);
}

