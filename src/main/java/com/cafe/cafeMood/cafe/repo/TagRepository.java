package com.cafe.cafeMood.cafe.repo;

import com.cafe.cafeMood.cafe.domain.tag.Tag;
import com.cafe.cafeMood.cafe.domain.tag.TagStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByStatus(TagStatus status);
}
