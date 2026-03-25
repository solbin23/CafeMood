package com.cafe.cafeMood.common.auth.controller;


import com.cafe.cafeMood.common.auth.dto.LoginRequest;
import com.cafe.cafeMood.common.auth.dto.UserInfoResponse;
import com.cafe.cafeMood.common.auth.service.AuthService;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
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

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login(@Valid @RequestBody LoginRequest request) {
        UserInfoResponse response = authService.login(request);

        return ResponseEntity.ok(authService.login(request));
    }
}
