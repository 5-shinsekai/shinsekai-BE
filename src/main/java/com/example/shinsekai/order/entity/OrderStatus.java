package com.example.shinsekai.order.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_OK("주문 완료"),
    ORDER_CANCLE("주문 취소");

    private final String orderStatus;

    @JsonValue
    public String getOrderStatus() {
        return orderStatus;
    }

    @JsonCreator
    public static OrderStatus fromString(String value) {
        for(OrderStatus orderStatus : OrderStatus.values()) {
            if(orderStatus.name().equalsIgnoreCase(value)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
