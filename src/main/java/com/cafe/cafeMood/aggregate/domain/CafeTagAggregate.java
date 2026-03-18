package com.cafe.cafeMood.aggregate.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Table(name = "cafe_tag_aggregate",
uniqueConstraints = @UniqueConstraint(name = "uk_cafe_tag_aggregate",columnNames = {"cafe_id","tag_id"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTagAggregate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "total_count", nullable = false)
    private Long totalCount;

    @Column(name = "score", nullable = false)
    private double score;


    private CafeTagAggregate(Long cafeId, Long tagId) {
        this.cafeId = cafeId;
        this.tagId = tagId;
        this.totalCount = 0L;
        this.score = 0.0;

    }

    public static CafeTagAggregate create(Long cafeId, Long tagId) {
        return new CafeTagAggregate(cafeId, tagId);
    }

    public void increase() {
        this.totalCount += 1;
        this.score = this.totalCount;
    }

    public void decrease() {
        if (this.totalCount > 0) {
            this.totalCount -= 1;
        }
        this.score = this.totalCount;
    }
}





