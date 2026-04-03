package com.cafe.cafeMood.user.dto.response;

import com.cafe.cafeMood.user.domain.Owner;

public record OwnerResponse(
        Long ownerId,
        Long userId,
        String email,
        String name,
        String phone,
        String businessNumber,
        String ownerName
) {
    public static OwnerResponse of(Owner owner) {
        return new OwnerResponse(
                owner.getId(),
                owner.getUser().getId(),
                owner.getUser().getEmail(),
                owner.getUser().getName(),
                owner.getUser().getPhone(),
                owner.getBusinessNumber(),
                owner.getOwnerName()
        );
    }
}
