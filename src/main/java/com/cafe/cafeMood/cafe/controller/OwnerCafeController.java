package com.cafe.cafeMood.cafe.controller;


import com.cafe.cafeMood.cafe.dto.request.CafeCreateRequest;
import com.cafe.cafeMood.cafe.dto.response.CafeResponse;
import com.cafe.cafeMood.cafe.service.OwnerCafeService;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cafe/owner")
@RequiredArgsConstructor
public class OwnerCafeController {

    private final OwnerCafeService ownerCafeService;

    @PostMapping("/{ownerId}")
    public ResponseEntity<ApiResponse<CafeResponse>> createCafe(@PathVariable Long ownerId,@Valid @RequestBody CafeCreateRequest request) {
       CafeResponse cafeResponse =  ownerCafeService.createCafe(ownerId,request);
        ResponseCode code = ResponseCode.CREATED;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, cafeResponse));
    }

}
