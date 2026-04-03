package com.cafe.cafeMood.user.domain;

import com.cafe.cafeMood.common.convert.BooleanToYnConverter;
import com.cafe.cafeMood.common.entity.BaseEntity;
import com.cafe.cafeMood.user.dto.request.UserSignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",nullable = false, length = 100)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "name",nullable = false, length = 100)
    private String name;

    @Column(name = "phone", length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    @Column(nullable = false)
    @Convert(converter = BooleanToYnConverter.class)
    private boolean serviceTermsConsent;

    @Column(nullable = false)
    @Convert(converter = BooleanToYnConverter.class)
    private boolean privacyConsent;

    @Column(nullable = false)
    @Convert(converter = BooleanToYnConverter.class)
    private boolean marketingConsent;

    @Column(nullable = false)
    private LocalDateTime consentedAt;

    public User(String email, String password, String name, String phone, UserRole role,boolean serviceTermsConsent, boolean privacyConsent, boolean marketingConsent, LocalDateTime consentedAt) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.serviceTermsConsent = serviceTermsConsent;
        this.privacyConsent = privacyConsent;
        this.marketingConsent = marketingConsent;
        this.consentedAt = consentedAt;

    }

    public static User create(UserSignUpRequest request, String encodedPassword) {

      return new User(request.email(),
              encodedPassword,
              request.name(),
              request.phone(),
              UserRole.USER,
              request.serviceTermsConsent(),
              request.privacyConsent(),
              request.marketingConsent(),
              LocalDateTime.now());

    }

    public static User createOwner(String email, String password, String name, String phone, boolean serviceTermsConsent, boolean privacyConsent, boolean marketingConsent) {
        return new User(email,password,name,phone,UserRole.OWNER,serviceTermsConsent,privacyConsent,marketingConsent,LocalDateTime.now());
    }


}
