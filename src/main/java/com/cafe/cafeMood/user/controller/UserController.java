package com.cafe.cafeMood.user.controller;

import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.auth.util.AuthUtil;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.user.dto.request.SignUpRequest;
import com.cafe.cafeMood.user.dto.response.UserResponse;
import com.cafe.cafeMood.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request){
               userService.signUp(request);

               return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo(HttpServletRequest request){
        LoginUser user = AuthUtil.getLoginUser(request);
        UserResponse response = userService.getMyInfo(user);
        ApiResponse<UserResponse> myInfo = ApiResponse.success(ResponseCode.SUCCESS,response);
        return ResponseEntity.ok(myInfo);
    }

    @GetMapping("/check")
    public ResponseEntity<String> adminCheck(HttpServletRequest request){
        LoginUser user = AuthUtil.getLoginUser(request);
        if (!user.isAdmin()) {
           throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok("admin");
    }
}
