package com.cafe.cafeMood.cafeList.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class CafeListItemId implements Serializable {
    private Long cafeListId;
    private Long cafeId;
}
