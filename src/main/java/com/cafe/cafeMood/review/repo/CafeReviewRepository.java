package com.cafe.cafeMood.review.repo;

import com.cafe.cafeMood.review.domain.CafeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CafeReviewRepository extends JpaRepository<CafeReview, Long> {

    //삭제되지 않은 리뷰만 조회
    Optional<CafeReview> findByIdAndDeletedAtIsNull(Long reviewId);
}
