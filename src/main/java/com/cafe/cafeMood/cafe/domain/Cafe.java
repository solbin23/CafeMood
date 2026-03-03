package com.cafe.cafeMood.cafe.domain;

import com.cafe.cafeMood.cafeList.domain.enums.CafeListStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "cafes")
public class Cafe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 200)
    private String shortDesc;

    private String phone;

    @Column(columnDefinition = "text")
    private String instagramUrl;

    @Column(columnDefinition = "text")
    private String websiteUrl;

    @Column(nullable = false)
    private boolean isIndependent = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CafeListStatus status = CafeListStatus.DRAFT;


}

