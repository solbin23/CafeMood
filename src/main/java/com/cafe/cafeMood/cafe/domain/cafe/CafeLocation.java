package com.cafe.cafeMood.cafe.domain.cafe;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cafe_locations")
public class CafeLocation {

    @Id
    private Long cafeId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private String address;
    private String address2;

    @Column(nullable = false, length = 50)
    private String region;

    @Column(nullable = false, length = 50)
    private String region2;

    @Column(length = 50)
    private String region3;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(columnDefinition = "text")
    private String kakaoPlaceUrl;

    @Column(columnDefinition = "text")
    private String naverPlaceUrl;
}
