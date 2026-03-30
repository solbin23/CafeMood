package com.cafe.cafeMood.review.repo;

import com.cafe.cafeMood.review.domain.CafeReviewTagSelection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeReviewTagSelectionRepository extends JpaRepository<CafeReviewTagSelection, Long> {

    List<CafeReviewTagSelection> findByReviewId(Long reviewId);

    void deleteByReviewId(Long reviewId);
}
