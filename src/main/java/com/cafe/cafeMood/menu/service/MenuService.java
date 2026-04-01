package com.cafe.cafeMood.menu.service;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.cafe.repo.cafe.CafeRepository;
import com.cafe.cafeMood.common.auth.dto.LoginUser;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import com.cafe.cafeMood.menu.dto.request.MenuCreateRequest;
import com.cafe.cafeMood.menu.dto.request.MenuUpdateRequest;
import com.cafe.cafeMood.menu.dto.response.MenuResponse;
import com.cafe.cafeMood.menu.repo.MenuRepository;
import com.cafe.cafeMood.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;


    public MenuResponse createMenu(LoginUser loginUser,Long cafeId, MenuCreateRequest request) {
        validateOwner(loginUser);

        Cafe cafe = getMyCafe(loginUser, cafeId);

        Menu menu = Menu.create(cafe,
                request.name(),
                request.category(),
                request.price(),
                request.description(),
                request.imageUrl(),
                request.displayOrder(),
                request.highlights());
        return MenuResponse.of(menuRepository.save(menu));
    }


    @Transactional(readOnly = true)
    public List<MenuResponse> getMenu(LoginUser loginUser,Long cafeId) {
        validateOwner(loginUser);
        getMyCafe(loginUser, cafeId);

        return menuRepository.findAllByCafeIdAndStatusNotOrderByCategoryAscDisplayOrderAsc(cafeId, MenuStatus.DELETED)
                .stream()
                .map(MenuResponse::of)
                .toList();
    }

    @Transactional
    public MenuResponse updateMenu(LoginUser loginUser,Long menuId, MenuUpdateRequest request) {
        validateOwner(loginUser);

        Menu menu = getMyMenu(loginUser, menuId);

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
    public void deleteMenu(LoginUser loginUser,Long menuId, String deletedBy) {
        validateOwner(loginUser);

        Menu menu = getMyMenu(loginUser,menuId);

        menuRepository.delete(menu);
    }


    private void validateOwner(LoginUser loginUser) {
        if (loginUser.role() != UserRole.OWNER) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }

    private Cafe getMyCafe(LoginUser loginUser, Long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(()-> new BusinessException(ErrorCode.CAFE_NOT_FOUND));

        if (!cafe.getOwnerId().equals(loginUser.userId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        return cafe;
    }

    private Menu getMyMenu(LoginUser loginUser, Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(()-> new BusinessException(ErrorCode.MENU_NOT_FOUND));

        if (!menu.getCafe().getOwnerId().equals(loginUser.userId())){
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        return menu;
    }
}
