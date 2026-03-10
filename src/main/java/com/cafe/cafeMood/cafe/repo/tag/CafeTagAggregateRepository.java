package com.cafe.cafeMood.cafe.repo.tag;

import com.cafe.cafeMood.cafe.domain.tag.CafeTagAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CafeTagAggregateRepository extends JpaRepository<CafeTagAggregate, CafeTagAggregate.Pk> {

    // total_count += 1
    @Modifying
    @Query(value = """
INSERT INTO cafe_tag_aggregate (cafe_id, tagid, total_count, unique_user_count, last_vote_date) 
VALUES (:cafeId, :tagId, 1, :uniqueInc, now())
ON CONFLICT (cafe_id, tag_id)
DO UPDATE SET
    total_count = cafe_tag_aggregate.total_count + 1,
    unique_user_count = cafe_tag_aggregate.unique_user_count + :uniqueInc,
    last_voted_at = now()
    """, nativeQuery = true)
    void upsertIncrement(long cafeId, long tagId, long uniqueInc);

    @Query(value = """
    SELECT * FROM cafe_tag_aggregate
    WHERE cafe_id = :cafeId
    ORDER BY total_count DESC , unique_user_count DESC, last_vote_date DESC
    LIMIT :limit
    """, nativeQuery = true)
    List<CafeTagAggregate> findByCafeId(long cafeId, int limit);
}

