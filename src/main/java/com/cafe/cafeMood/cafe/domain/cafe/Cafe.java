package com.cafe.cafeMood.cafe.domain.cafe;

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

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Cafe name is null or empty");
        }
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
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("name must not be null or empty");
        }
        this.name = name;
        touchDate();
    }

    public void updatePhone(String phone){
        this.phone = phone;
        touchDate();
    }

    public void changeShortDesc(String shortDesc){
        this.shortDesc = shortDesc;
        touchDate();
    }
    public void publish() {
        if(this.status == CafeStatus.PUBLISHED){
            throw new IllegalStateException("Cafe is already published");
        }
        this.status = CafeStatus.PUBLISHED;
        this.updateDate = Instant.now();
    }

    public void hide() {
        if (this.status == CafeStatus.HIDDEN){
            throw new IllegalStateException("Cafe is already hidden");
        }
        this.status = CafeStatus.HIDDEN;
        touchDate();
    }

    private void touchDate(){
        this.updateDate = Instant.now();
    }
}

