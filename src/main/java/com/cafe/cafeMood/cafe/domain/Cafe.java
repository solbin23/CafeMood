package com.cafe.cafeMood.cafe.domain;

import com.cafe.cafeMood.cafeList.domain.enums.CafeListStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "cafes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cafe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 200)
    private String shortDesc;

    private String phone;

    @Column(name = "create_date", nullable = false)
    @CreatedDate
    private Instant createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private Instant updateDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CafeStatus status = CafeStatus.DRAFT;

    private Cafe(String name, String shortDesc, String phone) {
        this.name = name;
        this.shortDesc = shortDesc;
        this.phone = phone;
        this.createDate = Instant.now();
        this.updateDate = Instant.now();
        this.status = CafeStatus.DRAFT;
    }

    public static Cafe create(String name, String shortDesc, String phone) {
        return new Cafe(name,shortDesc,phone);
    }

    public void updateName(String name) {
        this.name = name;
        this.updateDate = Instant.now();
    }

    public void updatePhone(String phone){
        this.phone = phone;
        this.updateDate = Instant.now();
    }
    public void publish() {
        this.status = CafeStatus.PUBLISHED;
    }

    public void hide() {
        this.status = CafeStatus.HIDDEN;
    }
}

