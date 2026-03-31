package com.cafe.cafeMood.menu.domain;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.common.entity.BaseEntity;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(name = "cafe_id", insertable = false , updatable = false)
    private Long cafeId;

    @Column(name = "name",nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category",nullable = false)
    private MenuCategory category;

    @Column(name = "price")
    private Integer price;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false, length = 30)
    private MenuStatus status;

    @ElementCollection
    @CollectionTable(name = "menu_highlights",joinColumns = @JoinColumn(name = "menu_id"))
    @Column(name = "highlight")
    private List<String> highlight;

    private Menu(Long cafeId, String name, MenuCategory category, Integer price, String description, String imageUrl, Integer displayOrder,  List<String> highlight) {
        this.cafeId = cafeId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.status = MenuStatus.ON_SALE;
        this.highlight = highlight == null ? new ArrayList<>() : new ArrayList<>(highlight);
    }

    public static Menu create(Long cafeId,
                              String name,
                              MenuCategory category,
                              Integer price,
                              String description,
                              String imageUrl,
                              Integer displayOrder,
                              List<String> highlight){
        return new Menu(cafeId,name,category,price,description,imageUrl,displayOrder,highlight);
    }

    public void updateInfo(String name, MenuCategory category, Integer price, String description, String imageUrl, Integer displayOrder, List<String> highlight) {
        validateDeleted();
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.highlight = highlight == null ? new ArrayList<>() : new ArrayList<>(highlight);
    }



    public void soldOut() {
        validateDeleted();
        this.status = MenuStatus.SOLD_OUT;
    }

    public  void OnSale() {
        validateDeleted();
        this.status = MenuStatus.ON_SALE;
    }

    public void hide(){
        validateDeleted();
        this.status = MenuStatus.HIDDEN;
    }

    public void delete(String deletedBy){
        if (this.status == MenuStatus.DELETED) {
            throw new BusinessException(ErrorCode.MENU_DELETED);
        }
        markDeleted(deletedBy);
        this.status = MenuStatus.DELETED;
    }


    private void validateDeleted() {
        if(this.status == MenuStatus.DELETED) {
            throw new BusinessException(ErrorCode.MENU_DELETED);
        }
    }
}
