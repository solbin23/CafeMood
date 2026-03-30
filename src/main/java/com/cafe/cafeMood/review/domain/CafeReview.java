package com.cafe.cafeMood.review.domain;

import com.cafe.cafeMood.cafe.domain.cafe.Cafe;
import com.cafe.cafeMood.common.entity.BaseEntity;
import com.cafe.cafeMood.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafe cafe;

    @Column(name = "user_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "content", length = 2000)
    private String content;

    @Builder
    public CafeReview(Cafe cafe, User user, String content) {
     this.cafe = cafe;
     this.user = user;
     this.content = content;
    }

    public void update(String content) {
        this.content = content;
    }

    public void delete(String deletedBy){
        markDeleted(deletedBy);
    }
}
