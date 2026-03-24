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

    @Column(name = "active", nullable = false)
    private boolean active;

    private User(String email, String password, String name, String phone, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.active = true;
    }
    private User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static User user(String email, String password, String name, String phone) {
        return new User(email, password, name, phone, UserRole.USER);
    }

    public static User owner(String email, String password, String name, String phone) {
        return new User(email, password, name, phone, UserRole.OWNER);
    }

    public static User admin(String email, String password, String name,  String phone) {
        return new User(email, password, name,  phone, UserRole.ADMIN);
    }

    public static User login(String loginId, String password){
        return new User(loginId, password);
    }

    public void deactivate() {
        this.active = false;
    }
}
