package com.salang.backend.domain.user.entity;

public enum Region {
    SEOUL("서울특별시"),
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    GWANGJU("광주광역시"),
    INCHEON("인천광역시");

    private final String label;

    Region(String label){ this.label = label; }

    public String getLabel() { return label; }
}
