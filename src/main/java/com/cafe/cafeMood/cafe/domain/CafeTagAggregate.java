package com.cafe.cafeMood.cafe.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Table(name = "cafe_tag_aggregate")
@IdClass(CafeTagAggregate.Pk.class)
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


    protected CafeTagAggregate() {}

    public static class Pk implements Serializable {
        public Long cafeId;
        public Long tagId;
        public Pk() {}
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





