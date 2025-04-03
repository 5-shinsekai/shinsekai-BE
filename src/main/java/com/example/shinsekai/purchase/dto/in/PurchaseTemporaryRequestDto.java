package com.example.shinsekai.purchase.dto.in;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@ToString
@Data
public class PurchaseTemporaryRequestDto {
    private String userId;
    private double amount;
    private List<String> productCodeList;
    private List<String> couponUuid;
    private List<String> giftCertificationUuid;
}
