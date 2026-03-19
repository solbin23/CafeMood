package com.cafe.cafeMood.user.controller;

import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.user.dto.request.SignUpRequest;
import com.cafe.cafeMood.user.dto.response.UserResponse;
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
@RequestMapping("/cafe/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> signUpUser(@Valid @RequestBody SignUpRequest request){
               UserResponse response = userService.signUpUser(request);

               return ResponseEntity.status(ResponseCode.CREATED.status())
                       .body(ApiResponse.success(ResponseCode.CREATED, response));
    }
}
