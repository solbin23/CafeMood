package com.cafe.cafeMood.menu.service;

import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import com.cafe.cafeMood.menu.dto.request.MenuCreateRequest;
import com.cafe.cafeMood.menu.dto.request.MenuUpdateRequest;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.menu.repo.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;

    @Transactional
    public MenuResponse createMenu(MenuCreateRequest request) {
        Menu menu = Menu.create(request.cafeId(),
                request.name(),
                request.category(),
                request.price(),
                request.description(),
                request.imageUrl(),
                request.displayOrder(),
                request.highlights());
        return MenuResponse.of(menuRepository.save(menu));
    }


    public List<MenuResponse> getMenu(Long cafeId) {
        return menuRepository.findAllByCafeIdAndStatusNotOrderByCategoryAscDisplayOrderAsc(cafeId, MenuStatus.DELETED)
                .stream()
                .map(MenuResponse::of)
                .toList();
    }

    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuUpdateRequest request) {
        Menu menu = menuRepository.findByIdAndStatusNot(menuId, MenuStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        menu.updateInfo(request.name(),
                request.category(),
                request.price(),
                request.description(),
                request.imageUrl(),
                request.displayOrder(),
                request.highlights());

        return MenuResponse.of(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId, String deletedBy) {
        Menu menu = menuRepository.findByIdAndStatusNot(menuId, MenuStatus.DELETED)
                .orElseThrow(()-> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        menu.delete(deletedBy);
    }
}
