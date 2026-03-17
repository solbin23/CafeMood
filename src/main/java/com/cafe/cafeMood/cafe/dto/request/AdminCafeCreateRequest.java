package com.cafe.cafeMood.cafe.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import javax.xml.stream.Location;
import java.math.BigDecimal;

public record AdminCafeCreateRequest(@NotBlank String name,
                                     String shortDesc,
                                     String phone,
                                     @NotNull @Valid Location location) {
    public record Location(@NotBlank String address1,
                           String address2,
                           @NotBlank String region1,
                           @NotBlank String region2,
                           String region3,
                           BigDecimal latitude,
                           BigDecimal longitude) {}
}
