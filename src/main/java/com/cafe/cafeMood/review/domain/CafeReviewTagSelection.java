package com.cafe.cafeMood.review.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cafe_review_tag_selections",
uniqueConstraints = {@UniqueConstraint(name = "uk_review_tag_selection_review_tag", columnNames = {"review_id","tag_id"})})
public class CafeReviewTagSelection{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id", nullable = false)
    private Long reviewId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    private CafeReviewTagSelection(Long reviewId, Long tagId) {
        this.reviewId = reviewId;
        this.tagId = tagId;
    }


    public static CafeReviewTagSelection of(Long reviewId, Long tagId) {
        return new CafeReviewTagSelection(reviewId, tagId);
    }
}
