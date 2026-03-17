package com.cafe.cafeMood.menu.domain;

public enum MenuStatus {
    ON_SALE,
    SOLD_OUT,
    HIDDEN,
    DELETED;

    public boolean visible(){
        return this == ON_SALE || this == SOLD_OUT;
    }

    public boolean orderAble(){
        return this == ON_SALE;
    }

    public boolean deleted(){
        return this == DELETED;
    }
}
