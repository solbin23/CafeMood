package com.cafe.cafeMood.cafe.controller;


import com.cafe.cafeMood.cafe.dto.request.cafe.CafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.response.cafe.CafeResponse;
import com.cafe.cafeMood.cafe.service.CafeService;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cafe")
@RequiredArgsConstructor
public class CafeController {

    private final CafeService cafeService;

    @PostMapping
    public ResponseEntity<ApiResponse<CafeResponse>> createCafe(@Valid @RequestBody CafeCreateRequest request) {
       CafeResponse cafeResponse =  cafeService.createCafe(request);
        ResponseCode code = ResponseCode.CREATED;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, cafeResponse));
    }

}
