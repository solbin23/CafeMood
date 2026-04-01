package com.cafe.cafeMood.menu.controller;


import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.auth.util.AuthUtil;
import com.cafe.cafeMood.common.response.ApiResponse;
import com.cafe.cafeMood.common.response.ResponseCode;
import com.cafe.cafeMood.menu.dto.request.MenuCreateRequest;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.menu.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cafe/mood")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/owner/{cafeId}/menus")
    public ResponseEntity<ApiResponse<MenuResponse>> createMenu(HttpServletRequest request, @PathVariable Long cafeId, @Valid @RequestBody MenuCreateRequest menuCreateRequest) {

        LoginUser loginUser = AuthUtil.getLoginUser(request);
        MenuResponse response = menuService.createMenu(loginUser,cafeId,menuCreateRequest);
        ResponseCode code = ResponseCode.SUCCESS;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, response));

    }

    @GetMapping("/owner/{cafeId}/menus")
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenus(HttpServletRequest request, @PathVariable Long cafeId) {
        LoginUser loginUser = AuthUtil.getLoginUser(request);
        List<MenuResponse> response = menuService.getMenu(loginUser,cafeId);
        ResponseCode code = ResponseCode.SUCCESS;

        return ResponseEntity.status(code.status())
                .body(ApiResponse.success(code, response));
    }
}
