package com.cafe.cafeMood.cafe.controller;


import com.cafe.cafeMood.cafe.dto.request.CafeSearchRequest;
import com.cafe.cafeMood.cafe.dto.response.CafeDetailResponse;
import com.cafe.cafeMood.cafe.dto.response.CafeListResponse;
import com.cafe.cafeMood.cafe.dto.response.CafeSearchResponse;
import com.cafe.cafeMood.cafe.service.UserCafeService;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "검색 및 조회 유저컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/mood")
public class CafeSearchController {

    private final UserCafeService cafeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CafeListResponse>>> getCafes() {

        List<CafeListResponse> response = cafeService.getPublishedCafes();
        ResponseCode code = ResponseCode.SUCCESS;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, response));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<CafeSearchResponse>>> searchCafes(@Valid @RequestBody CafeSearchRequest request) {

        List<CafeSearchResponse> response = cafeService.searchCafeMood(request);
        ResponseCode code = ResponseCode.SUCCESS;

        return  ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, response));
    }

    @GetMapping("/{cafeId}")
    public ResponseEntity<ApiResponse<CafeDetailResponse>> getCafeDetail(@PathVariable Long cafeId) {
        CafeDetailResponse response = cafeService.getCafeDetail(cafeId);
        ResponseCode code = ResponseCode.SUCCESS;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, response));
    }
}
