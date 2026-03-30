package com.cafe.cafeMood.review.repo;

import com.cafe.cafeMood.review.domain.CafeReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeReviewRepository extends JpaRepository<CafeReview, Long> {

    //삭제되지 않은 리뷰만 조회
    Optional<CafeReview> findByIdAndDeletedAtIsNull(Long reviewId);

    boolean existsByCafeIdAndUserId(Long cafeId, Long userId);
    Optional<CafeReview> findByIdAndUserId(Long reviewId, Long userId);
    List<CafeReview> findByCafeIdOrderByCreatedAtDesc(Long cafeId);
}
