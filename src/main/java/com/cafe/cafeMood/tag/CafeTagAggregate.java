package com.cafe.cafeMood.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Table(name = "cafe_tag_aggregate")
@IdClass(CafeTagAggregate.Pk.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTagAggregate {

    @Id
    @Column(name = "cafe_id",nullable = false)
    private Long cafeId;

    @Id
    @Column(name = "tag_id",nullable = false)
    private Long tagId;

    @Column(name = "total_count", nullable = false)
    private int totalCount;

    @Column(name = "unique_user_count", nullable = false)
    private int uniqueUserCount;

    @Column(name = "last_vote_date", nullable = false)
    private Instant lastVoteDate = Instant.now();


    private CafeTagAggregate(Long cafeId, Long tagId) {
        this.cafeId = cafeId;
        this.tagId = tagId;
        this.totalCount = 0;
        this.uniqueUserCount = 0;
        this.lastVoteDate = Instant.now();

    }

    public static CafeTagAggregate create(Long cafeId, Long tagId){
       return new CafeTagAggregate(cafeId, tagId);
    }

    public void increase(int totalInc, int uniqueInc) {
        this.totalCount += totalInc;
        this.uniqueUserCount += uniqueInc;
        this.lastVoteDate = Instant.now();
    }
    public static class Pk implements Serializable {
        public Long cafeId;
        public Long tagId;
        public Pk() {}

        public Pk(Long cafeId, Long tagId) {
            this.cafeId = cafeId;
            this.tagId = tagId;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Pk pk)){
            return false;
        }
        return Objects.equals(cafeId,pk.cafeId) && Objects.equals(tagId,pk.tagId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(cafeId,tagId);
    }
}





