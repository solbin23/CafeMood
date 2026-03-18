package com.cafe.cafeMood.review.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cafe_reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "content", length = 2000)
    private String content;

    private CafeReview(Long cafeId, Long userId, Integer rating, String content) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
    }

    public static CafeReview of(Long cafeId, Long userId, Integer rating, String content) {
        return new CafeReview(cafeId, userId, rating, content);
    }

    public void update(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void delete(String deletedBy){
        markDeleted(deletedBy);
    }
}
