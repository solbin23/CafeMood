package com.cafe.cafeMood.tag.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60)
    private TagCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TagStatus status;

    @Column(nullable = false)
    private int sortOrder;


    public Tag(String name, TagCategory category, TagStatus status, int sortOrder) {
        this.name = name;
        this.category = category;
        this.status = TagStatus.ACTIVE;
        this.sortOrder = sortOrder;
    }

    public void hide() {
        this.status = TagStatus.HIDDEN;
    }

    public void activate(){
        this.status = TagStatus.ACTIVE;
    }

    public boolean isActive(){
        return this.status == TagStatus.ACTIVE;
    }
}
