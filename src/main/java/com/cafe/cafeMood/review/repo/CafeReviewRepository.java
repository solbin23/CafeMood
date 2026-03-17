package com.cafe.cafeMood.review.repo;

import com.cafe.cafeMood.review.domain.CafeReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeReviewRepository extends JpaRepository<CafeReview, Long> {
}
