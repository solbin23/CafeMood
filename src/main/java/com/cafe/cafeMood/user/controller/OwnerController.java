package com.cafe.cafeMood.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/owners")
public class OwnerController {

//    private final UserService userService;
//
//    @PostMapping
//    public ResponseEntity<ApiResponse<UserResponse>> signUpOwner(@Valid @RequestBody SignUpRequest request) {
//        UserResponse owner = userService.signUpOwner(request);
//
//        return ResponseEntity.status(ResponseCode.CREATED.status())
//                .body(ApiResponse.success(ResponseCode.CREATED, owner));
//    }
}
