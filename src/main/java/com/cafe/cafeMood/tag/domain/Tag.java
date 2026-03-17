package com.cafe.cafeMood.tag.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tags")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 60)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TagStatus status;


    private Tag(String name, String slug) {
        this.name = name;
        this.slug = slug;
        this.status = TagStatus.ACTIVE;
    }

    public static Tag create(String name, String slug) {
        return new Tag(name, slug);
    }

    public void hide() {
        this.status = TagStatus.HIDDEN;
    }

    public void activate(){
        this.status = TagStatus.ACTIVE;
    }

    public boolean isActive(){
        return this.status == TagStatus.ACTIVE;
    }
}
