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
uniqueConstraints = @UniqueConstraint(name = "uk_cafe_tag_aggregate_cafe_tag",columnNames = {"cafe_id","tag_id"}),indexes = {
        @Index(name = "idx_cafe_tag_aggregate_tag_id", columnList = "tag_id"),@Index(name = "ind_cafe_tag_score_cafe_id", columnList = "cafe_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTagAggregate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "score", nullable = false)
    private double score;


    public CafeTagAggregate(Long cafeId, Long tagId, double score) {
        this.cafeId = cafeId;
        this.tagId = tagId;
        this.score = score;

    }

}





