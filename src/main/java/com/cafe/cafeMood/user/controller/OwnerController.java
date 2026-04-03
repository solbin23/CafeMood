package com.cafe.cafeMood.user.controller;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.auth.util.AuthUtil;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.user.domain.Owner;
import com.cafe.cafeMood.user.dto.request.OwnerSignUpRequest;
import com.cafe.cafeMood.user.dto.response.OwnerResponse;
import com.cafe.cafeMood.user.service.OwnerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<OwnerResponse>> signUpOwner(@Valid @RequestBody OwnerSignUpRequest request) {
        OwnerResponse owner = ownerService.signUp(request);

        return ResponseEntity.status(ResponseCode.OWNER_CREATED.status())
                .body(ApiResponse.success(ResponseCode.OWNER_CREATED, owner));
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<OwnerResponse>> getInfo(HttpServletRequest request) {
        LoginUser loginUser = AuthUtil.getLoginUser(request);

        OwnerResponse owner = OwnerResponse.of(ownerService.getOwnerProfile(loginUser));

        return ResponseEntity.status(ResponseCode.SUCCESS.status())
                .body(ApiResponse.success(ResponseCode.SUCCESS, owner));
    }
}
