package com.example.shinsekai.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum PriceRangeType {
    UNDER_10K(1, "1만원 미만", 0, 9999),
    RANGE_10K(2, "1만원대", 10000, 19999),
    RANGE_20K(3, "2만원대", 20000, 29999),
    RANGE_30K(4, "3만원대", 30000, 39999),
    RANCE_40K(5, "4만원대", 40000, 49999),
    OVER_50K(6, "5만원 이상", 50000, Integer.MAX_VALUE);

    private final int id;
    private final String name;
    private final int min;
    private final int max;

    PriceRangeType(int id, String name, int min, int max) {
        this.id = id;
        this.name = name;
        this.min = min;
        this.max = max;
    }

    public static Optional<PriceRangeType> fromCode(int id) {
        return Arrays.stream(values())
                .filter(e -> e.id == id)
                .findFirst();
    }

    public static Optional<PriceRangeType> fromPrice(int price) {
        return Arrays.stream(values())
                .filter(e -> price >= e.min && price <= e.max)
                .findFirst();
    }
}
