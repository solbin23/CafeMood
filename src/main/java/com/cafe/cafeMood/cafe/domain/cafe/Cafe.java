package com.cafe.cafeMood.cafe.domain.cafe;

import com.cafe.cafeMood.common.entity.BaseEntity;
import com.cafe.cafeMood.common.exception.BusinessException;
import com.cafe.cafeMood.common.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "cafes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Cafe extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id",nullable = false)
    private Long ownerId;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "address",nullable = false, length = 255)
    private String address;

    @Column(length = 200)
    private String shortDesc;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name="status",nullable = false, length = 20)
    private CafeStatus status;

    private Cafe(Long ownerId,String name, String address,String shortDesc, String phone) {

        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Cafe name is null or empty");
        }
        this.ownerId = ownerId;
        this.name = name;
        this.address = address;
        this.shortDesc = shortDesc;
        this.phone = phone;
        this.status = CafeStatus.DRAFT;
    }

    public static Cafe create(Long ownerId,String name, String address,String shortDesc, String phone) {
        return new Cafe(ownerId,name,address,shortDesc,phone);
    }

    public void updateInfo(String name, String address, String phone, String shortDesc){
            validateNotDeleted();
            this.name = name;
            this.address = address;
            this.shortDesc = shortDesc;
            this.phone = phone;
    }

    public void requestApproval(){
        if (!this.status.canRequestApproval()){
            throw new BusinessException(ErrorCode.INVALID_CAFE_STATE);
        }
        this.status = CafeStatus.PENDING;
    }

    public void publish(){
        if (!this.status.canPublish()){
            throw new BusinessException(ErrorCode.INVALID_CAFE_STATE);
        }
        this.status = CafeStatus.PUBLISHED;
    }

    public void hide(){
        validateNotDeleted();
        this.status = CafeStatus.HIDDEN;
    }

    public void suspend() {
        validateNotDeleted();
        this.status = CafeStatus.SUSPENDED;
    }

    public void delete(String deletedBy) {
        if (this.status == CafeStatus.DELETED) {
            throw new BusinessException(ErrorCode.CAFE_ALREADY_DELETED);
        }
        markDeleted(deletedBy);
        this.status = CafeStatus.DELETED;
    }


    private void validateCanOwnerEdit(){
        if(!this.status.canOwnerEdit()){
            throw new BusinessException(ErrorCode.INVALID_CAFE_STATE);
        }
    }
    private void validateNotDeleted() {
        if (this.status == CafeStatus.DELETED) {
            throw new IllegalArgumentException("삭제된 카페는 처리할 수 없습니다.");
        }
    }
}

