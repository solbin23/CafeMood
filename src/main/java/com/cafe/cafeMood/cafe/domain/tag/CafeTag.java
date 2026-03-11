package com.cafe.cafeMood.cafe.domain.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Table(name = "cafe_tag",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_cafe_tag",
                columnNames = {"cafe_id","tag_id"}
        )
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "created_date", nullable = false)
    private Instant createDate;

    @Column(name = "update_date", nullable = false)
    private Instant updateDate;

    private CafeTag(Long cafeId, Long tagId, int score) {
        this.cafeId = cafeId;
        this.tagId = tagId;
        this.score = score;
        this.createDate = Instant.now();
        this.updateDate = Instant.now();
    }

    public static CafeTag create(Long cafeId, Long tagId, int score) {
        return new CafeTag(cafeId, tagId, score);
    }

    public void updateScore(int score) {
        this.score = score;
        this.updateDate = Instant.now();
    }
}
