package com.example.shinsekai.common.enums;

import lombok.Getter;

@Getter
public enum SizeType {
    SHORT(1, "Short"),
    TALL(2, "Tall"),
    GRANDE(3, "Grande"),
    VENTI(4, "Venti"),
    TRENTA(5, "Trenta");

    private final int id;
    private final String name;

    SizeType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
