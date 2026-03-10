package com.cafe.cafeMood.cafe.repo.tag;

import com.cafe.cafeMood.cafe.domain.tag.CafeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CafeTagRepository extends JpaRepository<CafeTag, CafeTag.Pk> {

    @Modifying
    @Query(value = """
      INSERT INTO cafe_tag (cafe_id, tag_id, score, creat_date)
      VALUE (:cafeId, :tagId, :score, now())
      ON CONFLICT (cafe_id, tag_id)
      DO UPDATE SET score = EXCLUDED.score
      """, nativeQuery = true)
    void upsertScore(long cafeId, long tagId, long score);

    @Modifying
    @Query(value = """
     DELETE FROM cafe_tag
     WHERE cafe_id = : cafeId
        AND tag_id NOT IN (:keepTagIds)
    """, nativeQuery = true)
    void deleteNotIn(long cafeId, List<Long> keepTagIds);

    @Query(value = """
     SELECT * FROM cafe_tag WHERE cafe_id = :cafeId ORDER BY  score DESC 
    """, nativeQuery = true)
    List<CafeTag> findByCafeIdOrderByScoreDesc(long cafeId);
}
