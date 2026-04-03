package com.cafe.cafeMood.user.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owner")
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id",nullable = false,unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, unique = true, length = 20)
    private String businessNumber;

    @Column(nullable = false, length = 30)
    private String ownerName;


    private Owner(User user, String businessNumber, String ownerName) {
        this.user = user;
        this.businessNumber = businessNumber;
        this.ownerName = ownerName;
    }

    public static Owner create(User user, String businessNumber, String ownerName) {
        return new Owner(user, businessNumber, ownerName);
    }

}
