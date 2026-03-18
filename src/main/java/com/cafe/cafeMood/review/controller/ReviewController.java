package com.cafe.cafeMood.review.controller;


import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.review.dto.request.CafeReviewCreateRequest;
import com.cafe.cafeMood.review.dto.request.CafeReviewUpdateRequest;
import com.cafe.cafeMood.review.dto.response.CafeReviewResponse;
import com.cafe.cafeMood.review.service.CafeReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/reviews")
public class ReviewController {

    private final CafeReviewService reviewService;
    private final CafeReviewService cafeReviewService;

    @PostMapping
    public ResponseEntity<ApiResponse<CafeReviewResponse>> createReview(@Valid @RequestBody CafeReviewCreateRequest request) {
        CafeReviewResponse response = reviewService.createReview(request);

        return ResponseEntity.status(ResponseCode.CREATED.status())
                .body(ApiResponse.success(ResponseCode.CREATED, response));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<CafeReviewResponse>> updateReview(@PathVariable Long reviewId,@Valid @RequestBody CafeReviewUpdateRequest request) {
        CafeReviewResponse response = reviewService.updateReview(reviewId, request);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS,response));
    }


    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<CafeReviewResponse>> deleteReview(@PathVariable Long reviewId) {
        cafeReviewService.deleteReview(reviewId,"user");
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETED));
    }
}
