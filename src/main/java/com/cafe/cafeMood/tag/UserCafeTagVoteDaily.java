package com.cafe.cafeMood.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "user_cafe_tag_vote_daily", uniqueConstraints = @UniqueConstraint(name = "uk_daily_vote", columnNames = {"cafe_id","user_id","tag_id","vote_date"}),
        indexes = {@Index(name = "idx_daily_cafe",columnList = "cafe_id"),
                   @Index(name = "idx_daily_user", columnList = "user_id"),
                   @Index(name = "idx_daily_vote_date", columnList = "vote_date")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCafeTagVoteDaily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id",nullable = false)
    private long cafeId;


    @Column(name = "user_id",nullable = false)
    private long userId;


    @Column(name = "tag_id",nullable = false)
    private long tagId;


    @Column(name = "vote_date",nullable = false)
    private LocalDate voteDate;

    @Column(name = "create_date",nullable = false)
    private Instant createDate = Instant.now();


    private UserCafeTagVoteDaily(long cafeId,long userId, long tagId, LocalDate voteDate) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.tagId = tagId;
        this.voteDate = voteDate;
        this.createDate = Instant.now();
    }

    public static UserCafeTagVoteDaily create(Long cafeId, Long userId, Long tagId, LocalDate voteDate) {

        if (cafeId == null || cafeId <= 0) {
            throw new IllegalArgumentException("invalid cafe id");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("invalid user id");
        }
        if (tagId == null || tagId <= 0) {
            throw new IllegalArgumentException("invalid tag id");
        }
        return new UserCafeTagVoteDaily(cafeId, userId, tagId, voteDate);
    }

}
