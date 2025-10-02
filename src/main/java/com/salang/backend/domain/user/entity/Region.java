package com.salang.backend.domain.user.entity;

public enum Region {
    SEOUL("서울특별시"),
    BUSAN("부산광역시");

    private final String displayName;

    Region(String displayName){ this.displayName = displayName; }

    public String getDisplayName() { return displayName; }
}
