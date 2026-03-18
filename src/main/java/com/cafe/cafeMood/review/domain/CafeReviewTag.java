package com.cafe.cafeMood.review.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "cafe_review_tags",
               uniqueConstraints = @UniqueConstraint(name = "uk_cafe_review_tag",columnNames = {"review_id","tag_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeReviewTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "cafe_id",nullable = false)
    private Long cafeId;

    @Column(name = "tag_id",nullable = false)
    private Long tagId;

    private CafeReviewTag(Long reviewId, Long cafeId, Long tagId) {
        this.reviewId = reviewId;
        this.cafeId = cafeId;
        this.tagId = tagId;
    }

    public static CafeReviewTag create(Long reviewId, Long cafeId, Long tagId) {
        return new CafeReviewTag(reviewId, cafeId, tagId);
    }
}
