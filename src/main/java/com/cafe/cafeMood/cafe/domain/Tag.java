package com.cafe.cafeMood.cafe.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tag", uniqueConstraints = @UniqueConstraint(name = "uk_tag_slug",columnNames = "slug"))
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 60)
    private String slug;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TagStatus status = TagStatus.ACTIVE;
}
