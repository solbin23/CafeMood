package com.cafe.cafeMood.cafe.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Table(name = "user_cafe_tag_vote_daily", uniqueConstraints = @UniqueConstraint(name = "uk_daily_vote", columnNames = {"cafe_id","user_id","tag_id","vote_date"}))
@IdClass(UserCafeTagVoteDaily.Pk.class)
public class UserCafeTagVoteDaily {

    @Id
    @Column(name = "cafe_id",nullable = false)
    private long cafeId;

    @Id
    @Column(name = "user_id",nullable = false)
    private long userId;

    @Id
    @Column(name = "tag_id",nullable = false)
    private long tagId;

    @Id
    @Column(name = "vote_date",nullable = false)
    private LocalDate voteDate;

    @Column(name = "create_date",nullable = false)
    private Instant createDate = Instant.now();


    protected UserCafeTagVoteDaily() {}

    public UserCafeTagVoteDaily(long cafeId,long userId, long tagId, LocalDate voteDate) {
        this.cafeId = cafeId;
        this.userId = userId;
        this.tagId = tagId;
        this.voteDate = voteDate;
    }

    public static class Pk implements Serializable {
        public Long cafeId;
        public Long userId;
        public Long tagId;
        public LocalDate voteDate;
        public Pk(){}

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Pk pk)) return false;
        return Objects.equals(cafeId,pk.cafeId)
                && Objects.equals(userId,pk.userId)
                && Objects.equals(tagId,pk.tagId)
                && Objects.equals(voteDate,pk.voteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cafeId,userId,tagId,voteDate);
    }


}
