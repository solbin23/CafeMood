package com.cafe.cafeMood.user.domain;

import com.cafe.cafeMood.common.entity.BaseEntity;
import com.cafe.cafeMood.user.dto.request.SignUpRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public User(String email, String password, String name, String phone, UserRole role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.role = role;

    }
    private User(String email, String password,String phone,UserRole role){
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
    }

    public static User owner(String email, String password, String name, String phone,UserRole role) {
        return new User(email, password, name, phone, role );
    }
    public static User create(String email, String password, String phone, UserRole role) {
     return new User(email, password,phone, role);
    }

    public static User create(SignUpRequest request, String encodedPassword) {
        if (request.role() == UserRole.OWNER) {
            return owner(request.email(),
                    encodedPassword,
                    request.name(),
                    request.phone(),
                    request.role());
        }
        return create(request.email(),encodedPassword,request.phone(),request.role());
    }


}
