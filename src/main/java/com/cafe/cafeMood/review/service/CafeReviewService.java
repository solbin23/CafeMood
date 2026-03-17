package com.cafe.cafeMood.review.service;

import com.cafe.cafeMood.review.repo.CafeReviewRepository;
import com.cafe.cafeMood.review.repo.CafeReviewTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeReviewService {

    private final CafeReviewRepository reviewRepository;
    private final CafeReviewTagRepository reviewTagRepository;

}
