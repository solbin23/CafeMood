package com.cafe.cafeMood.menu.repo;

import com.cafe.cafeMood.menu.domain.Menu;
import com.cafe.cafeMood.menu.domain.MenuCategory;
import com.cafe.cafeMood.menu.domain.MenuStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {


    @Query("""
        select m 
        from Menu m
        where m.cafe.id in :cafeIds
        and m.category = :category
    """)
    List<Menu> findMenusByCafeIdsAndCategory(@Param("cafeIds") List<Long> cafeIds, @Param("category") MenuCategory category);

    List<Menu> findByCafeIdOrderByCategoryAscIdAsc(Long cafeId);
    Optional<Menu> findByIdAndStatusNot(Long id, MenuStatus status);

    List<Menu> findAllByCafeIdAndStatusNotOrderByCategoryAscDisplayOrderAsc(Long cafeId, MenuStatus status);

    List<Menu> findAllByCafeIdAndCategoryAndStatusNotOrderByDisplayOrderAsc(Long cafeId, MenuCategory category, MenuStatus status);

    List<Menu> findTop3ByCafeIdAndCategoryAndStatusOrderByDisplayOrderAsc(Long cafeId, MenuCategory category, MenuStatus status);
}
