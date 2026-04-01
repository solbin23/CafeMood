package com.cafe.cafeMood.tag.repo;

import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.domain.TagCategory;
import com.cafe.cafeMood.tag.domain.TagStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeTagRepository extends JpaRepository<Tag, Long> {

//    Optional<Tag> findByCafeIdAndTagId(Long cafeId,Long tagId);
//    List<Tag> findByCafeIdOrderByScoreDesc(Long cafeId);
//    void deleteByCafeIdAndTagIdNotIn(Long cafeId, List<Long> tagIds);
    List<Tag> findByStatus(TagStatus status);
    List<Tag> findByCategoryAndStatus(TagCategory category, TagStatus status);
    Optional<Tag> findByName(String name);
}
