package com.cafe.cafeMood.aggregate.repo;

import com.cafe.cafeMood.aggregate.domain.CafeTagAggregate;
import com.cafe.cafeMood.cafe.dto.response.RecommendCafeResponse;
import com.cafe.cafeMood.tag.dto.TagScoreSummary;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface CafeTagAggregateRepository extends JpaRepository<CafeTagAggregate, Long> {

   @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update CafeTagAggregate c 
        set c.score = c.score + 1
        where c.cafeId = :cafeId
        and c.tagId = :tagId
""")
    int incrementScore(@Param("cafeId") Long cafeId, @Param("tagId") Long tagId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        update CafeTagAggregate c
        set c.score = c.score -1 where c.cafeId = :cafeId
        and c.tagId = : tagId
        and c.score > 0
""")
    int decrementScore(@Param("cafeId") Long cafeId, @Param("tagId") Long tagId);

    @Query("""
        select new com.cafe.cafeMood.cafe.dto.response.RecommendCafeResponse(
        c.id,
        c.name, 
        c.address, 
        sum(cta.score))
        from Cafe c
        join CafeTagAggregate  cta on cta.cafeId =  c.id
        where c.status = com.cafe.cafeMood.cafe.domain.cafe.CafeStatus.PUBLISHED
        and cta.tagId in :tagIds
        group by c.id, c.name, c.address
        order by sum(cta.score) desc, c.id desc 
    """)
    List<RecommendCafeResponse> findRecommendCafes(@Param("tagIds") List<Long> tagIds, Pageable pageable);

    @Query("""
      select new com.cafe.cafeMood.tag.dto.TagScoreSummary(t.id,t.name,cta.score)
      from CafeTagAggregate cta
      join Tag t on t.id = cta.tagId
      where cta.cafeId = :cafeId
      and cta.score > 0
      and t.status = com.cafe.cafeMood.tag.domain.TagStatus.ACTIVE
      order by cta.score desc , t.sortOrder asc , t.id asc
""")
    List<TagScoreSummary> findTopTagsByCafeId(@Param("cafeId") Long cafeId, Pageable pageable);
}

