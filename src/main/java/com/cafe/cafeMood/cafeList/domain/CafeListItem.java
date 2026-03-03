package com.cafe.cafeMood.cafeList.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cafeList_items")
@Getter @Setter
public class CafeListItem {

    @EmbeddedId
    private CafeListItemId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafeList_id", nullable = false)
    private CafeList cafeList;
}
