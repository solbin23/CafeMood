package com.cafe.cafeMood.user.controller;


import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.common.auth.LoginRequest;
import com.cafe.cafeMood.user.dto.response.LoginResponse;
import com.cafe.cafeMood.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe")
public class LoginController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);

        return ResponseEntity.ok(ApiResponse.success(ResponseCode.SUCCESS, response));
    }
}
