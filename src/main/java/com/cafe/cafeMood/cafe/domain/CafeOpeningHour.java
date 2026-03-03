package com.cafe.cafeMood.cafe.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cafe_opening_hours", uniqueConstraints = @UniqueConstraint(columnNames = {"cafe_id","day_of_week"}))
public class CafeOpeningHour {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id", nullable = false)
    private Cafe cafe;

    @Column(name = "day_of_week",nullable = false)
    private short dayOfWeek;

    private LocalDateTime openTime;

    private LocalDateTime closeTime;

    @Column(nullable = false)
    private boolean isClosed = false;
}

