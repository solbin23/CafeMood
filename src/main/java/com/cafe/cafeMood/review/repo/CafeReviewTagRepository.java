package com.cafe.cafeMood.review.repo;

import com.cafe.cafeMood.review.domain.CafeReviewTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeReviewTagRepository extends JpaRepository<CafeReviewTag, Long> {

    List<CafeReviewTag> findAllByCafeId(Long cafeId);

    List<CafeReviewTag> findAllByReviewId(Long reviewId);

    void deleteAllByReviewId(Long reviewId);
}
