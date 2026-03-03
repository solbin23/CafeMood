package com.cafe.cafeMood.cafeList.domain;

import com.cafe.cafeMood.cafeList.domain.enums.CafeListStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafeList")
@Getter @Setter
public class CafeList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 200)
    private String subtitle;

    @Column(columnDefinition = "text")
    private String coverImageUrl;

    @Column(length = 50)
    private String region1;

    @Column(length = 50)
    private String region2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CafeListStatus status = CafeListStatus.PUBLISHED;

    @Column(nullable = false)
    private int sortOrder = 0;

    @Column(nullable = false)
    private Instant createDate = Instant.now();

    @Column(nullable = false)
    private Instant updateDate = Instant.now();

    @OneToMany(mappedBy = "cafeList", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC, cafeId ASC")
    private List<CafeListItem> items = new ArrayList<>();

}
