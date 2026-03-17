package com.cafe.cafeMood.cafe.repo.tag;

import com.cafe.cafeMood.tag.CafeTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeTagRepository extends JpaRepository<CafeTag, Long> {

    Optional<CafeTag> findByCafeIdAndTagId(Long cafeId,Long tagId);
    List<CafeTag> findByCafeIdOrderByScoreDesc(Long cafeId);
    void deleteByCafeIdAndTagIdNotIn(Long cafeId, List<Long> tagIds);
}
