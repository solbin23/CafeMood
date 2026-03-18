package com.cafe.cafeMood.review.controller;


import com.cafe.cafeMood.review.service.CafeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/reviews")
public class ReviewController {

    private final CafeReviewService reviewService;
}
