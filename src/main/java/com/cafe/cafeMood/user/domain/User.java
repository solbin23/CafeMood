package com.cafe.cafeMood.user.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users",
uniqueConstraints = {@UniqueConstraint(name = "uk_user_login_id", columnNames = "login_id"),
@UniqueConstraint(name = "uk_user_email",columnNames = "email")})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, length = 50)
    private String loginId;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "name",nullable = false, length = 100)
    private String name;

    @Column(name = "email",nullable = false, length = 100)
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRole role;

    @Column(name = "active", nullable = false)
    private boolean active;

    private User(String loginId, String password, String name, String email, String phone, UserRole role) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.active = true;
    }

    public static User createUser(String loginId, String password, String name, String email, String phone) {
        return new User(loginId, password, name, email, phone, UserRole.USER);
    }

    public static User createOwner(String loginId, String password, String name, String email, String phone) {
        return new User(loginId, password, name, email, phone, UserRole.OWNER);
    }

    public static User createAdmin(String loginId, String password, String name, String email, String phone) {
        return new User(loginId, password, name, email, phone, UserRole.ADMIN);
    }

    public boolean isUser() {
        return this.role == UserRole.USER;
    }
    public boolean isOwner(){
        return this.role == UserRole.OWNER;
    }

    public boolean isAdmin(){
        return this.role == UserRole.ADMIN;
    }

    public void deactivate() {
        this.active = false;
    }
}
