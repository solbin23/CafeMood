package com.cafe.cafeMood.cafe.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Entity
@Table(name = "cafe_submissions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CafeSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(length = 120)
    private String shortDesc;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CafeSubmissionStatus status;

    @Column(name = "review_comment", length = 255)
    private String reviewComment;

    @Column(name = "approved_cafe_id",nullable = false)
    private Long approvedCafeId;

    @Column(name = "create_date", nullable = false)
    private Instant createDate;

    @Column(name = "update_date",nullable = false)
    private Instant updateDate;


    private CafeSubmission(Long ownerUserId, String name, String shortDesc, String address) {
        this.ownerUserId = ownerUserId;
        this.name = name;
        this.shortDesc = shortDesc;
        this.address = address;
        this.status = CafeSubmissionStatus.REQUESTED;
        this.createDate = Instant.now();
        this.updateDate = Instant.now();
    }

    public static CafeSubmission create(Long ownerUserId, String name, String shortDesc, String address) {
        return new CafeSubmission(ownerUserId, name, shortDesc, address);
    }

    public void startReview(){
        this.status = CafeSubmissionStatus.REVIEW;
        update();
    }

    public void approve(Long cafeId){
        this.status = CafeSubmissionStatus.APPROVED;
        this.approvedCafeId = cafeId;

        update();
    }

    public void reject(String reviewComment){
        this.status = CafeSubmissionStatus.REJECTED;
        this.reviewComment = reviewComment;
        update();
    }

    private void update() {
        this.updateDate = Instant.now();
    }
}
