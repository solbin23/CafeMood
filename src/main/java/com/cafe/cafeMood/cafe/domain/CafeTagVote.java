package com.cafe.cafeMood.cafe.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity
@Getter
@Table(name = "cafe_tag_vote", indexes = {@Index(name = "idx_vote_cafe",columnList = "cafe_id")
,@Index(name = "idx_vote_tag",columnList = "tag_id"),
@Index(name = "idx_vote_user",columnList = "user_id")})
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
    private Instant createDate = Instant.now();

    protected CafeTagVote(){

    }
    public CafeTagVote(Long cafeId, Long userId, Long tagId) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.tagId = tagId;
    }
}
