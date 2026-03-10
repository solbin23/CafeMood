package com.cafe.cafeMood.cafe.domain.cafe;

import jakarta.persistence.*;

@Entity
@Table(name = "cafe_photos")
public class CafePhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id",nullable = false)
    private Cafe cafe;

    @Column(columnDefinition = "text",nullable = false)
    private String url;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false)
    private boolean isCover = false;
}
