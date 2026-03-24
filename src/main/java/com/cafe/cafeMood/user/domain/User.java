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

    public User(String email, String password, String name, String phone, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;

    }
    private User(String email, String password,UserRole role){
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User create(String email, String password, String name, String phone) {
        return new User(email, password, name, phone, UserRole.USER);
    }


}
