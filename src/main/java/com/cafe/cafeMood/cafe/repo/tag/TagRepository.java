package com.cafe.cafeMood.cafe.repo.tag;

import com.cafe.cafeMood.tag.domain.Tag;
import com.cafe.cafeMood.tag.domain.TagStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByStatus(TagStatus status);
}
