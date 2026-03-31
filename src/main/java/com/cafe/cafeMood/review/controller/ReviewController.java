package com.cafe.cafeMood.review.controller;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.auth.util.AuthUtil;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.review.dto.request.CafeReviewCreateRequest;
import com.cafe.cafeMood.review.dto.request.CafeReviewUpdateRequest;
import com.cafe.cafeMood.review.dto.response.CafeReviewResponse;
import com.cafe.cafeMood.review.service.CafeReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class ReviewController {

    private final CafeReviewService reviewService;

    @PostMapping("/mood/{cafeId}/reviews")
    public ResponseEntity<ApiResponse<CafeReviewResponse>> createReview(HttpServletRequest request, @PathVariable Long cafeId, @Valid @RequestBody CafeReviewCreateRequest reviewCreateRequest) {
        LoginUser loginUser = AuthUtil.getLoginUser(request);

        CafeReviewResponse response = reviewService.createReview(loginUser, cafeId,reviewCreateRequest);

        return ResponseEntity.status(ResponseCode.CREATED.status())
                .body(ApiResponse.success(ResponseCode.CREATED, response));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<CafeReviewResponse>> updateReview(HttpServletRequest request,@PathVariable Long reviewId,@Valid @RequestBody CafeReviewUpdateRequest reviewUpdateRequest) {
        LoginUser loginUser = AuthUtil.getLoginUser(request);
        CafeReviewResponse response = reviewService.updateReview(loginUser,reviewId, reviewUpdateRequest);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS,response));
    }


    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<CafeReviewResponse>> deleteReview(HttpServletRequest request,@PathVariable Long reviewId) {
        LoginUser loginUser = AuthUtil.getLoginUser(request);

        reviewService.deleteReview(loginUser,reviewId);
        return ResponseEntity.ok(ApiResponse.success(ResponseCode.DELETED));
    }
}
