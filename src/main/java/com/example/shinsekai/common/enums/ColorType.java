package com.example.shinsekai.common.enums;

import lombok.Getter;

@Getter
public enum ColorType {
    RED(1, "Red"),
    BLUE(2, "Blue"),
    BLACK(3, "Black"),
    WHITE(4, "White"),
    MINT(5, "Mint"),
    LAVENDER(6, "Lavender");

    private final int id;
    private final String name;

    ColorType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

