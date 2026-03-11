package com.cafe.cafeMood.cafe.domain.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@Table(name = "cafe_tag_vote")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTagVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Column(name = "tag_id",nullable = false)
    private Long tagId;

    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    public CafeTagVote(Long cafeId, Long userId, Long tagId) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.tagId = tagId;
        this.createDate = Instant.now();
    }

    public static CafeTagVote create(Long cafeId, Long userId, Long tagId) {
        return new CafeTagVote(cafeId, userId, tagId);
    }
}
