package com.cafe.cafeMood.cafe.domain.tag;

import jakarta.persistence.*;
import lombok.Getter;

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
@IdClass(CafeTag.Pk.class)
public class CafeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cafe_id", nullable = false)
    private Long cafeId;

    @Id
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    @Column(name = "score", nullable = false)
    private int score;

    @Column(name = "created_date", nullable = false)
    private Instant createDate = Instant.now();

    protected CafeTag(){}
    public CafeTag(Long cafeId, Long tagId, int score) {
        this.cafeId = cafeId;
        this.tagId = tagId;
        this.score = score;
    }

    public static class Pk implements Serializable {
        public Long cafeId;
        public Long tagId;
        public Pk(){}
        @Override
        public boolean equals(Object obj) {
            if(this == obj) {
                return true;
            }
            if (!(obj instanceof Pk pk)) {
                return false;
            }
            return Objects.equals(cafeId, pk.cafeId) && Objects.equals(tagId, pk.tagId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(cafeId, tagId);
        }
    }
}
