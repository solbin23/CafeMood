package com.cafe.cafeMood.menu.repo;

import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuCategory;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByIdAndStatusNot(Long id, MenuStatus status);

    List<Menu> findAllByCafeIdAndStatusNotOrderByCategoryAscDisplayOrderAsc(Long cafeId, MenuStatus status);

    List<Menu> findAllByCafeIdAndCategoryAndStatusNotOrderByDisplayOrderAsc(Long cafeId, MenuCategory category, MenuStatus status);

    List<Menu> findTop3ByCafeIdAndCategoryAndStatusOrderByDisplayOrderAsc(Long cafeId, MenuCategory category, MenuStatus status);
}
