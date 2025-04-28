package com.example.shinsekai.product.mapper;

import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.entity.ProductStatus;

import java.util.List;
import java.util.Map;

public class JsonProductMapper {

    public static ProductRequestDto toDto(Map<String, Object> rawJson) {

        String productName = (String) rawJson.getOrDefault("제품명", "이름 없음");
        double originalPrice = parsePrice(rawJson.get("정가"));
        double salePrice = parsePrice(rawJson.get("할인가"));
        int discountRate = calculateDiscountRate(originalPrice, salePrice);
        String contentImages = (String) rawJson.getOrDefault("상세정보HTML", "");
        String thumbnailUrl = extractThumbnailUrl(rawJson);
        int limit = 3; // 기본값 3

        return ProductRequestDto.builder()
                .productName(productName)
                .productPrice(salePrice)
                .productStatus(ProductStatus.SELLING) // 기본값 설정
                .productSummary(productName) // 상품 설명 없으므로 이름으로 대체
                .contentImages(contentImages)
                .thumbnailUrl(thumbnailUrl)
                .userPurchaseLimit(limit)
                .isFrozen(false)
                .isEngraving(false)
                .discountRate(discountRate)
                .build();
    }

    private static double parsePrice(Object priceObj) {
        if (priceObj instanceof Number) {
            return ((Number) priceObj).doubleValue();
        } else if (priceObj instanceof String str) {
            return Double.parseDouble(str.replace(",", ""));
        }
        return 0.0;
    }

    private static int calculateDiscountRate(double original, double sale) {
        if (original <= 0 || sale >= original) return 0;
        return (int) Math.round((1 - (sale / original)) * 100);
    }

    private static String extractThumbnailUrl(Map<String, Object> rawJson) {
        try {
            List<Map<String, Object>> list = (List<Map<String, Object>>) rawJson.get("썸네일이미지");
            if (list != null && !list.isEmpty()) {
                return (String) list.get(0).get("url");
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}

