package com.example.shinsekai.purchase.dto.in;

import com.example.shinsekai.purchase.vo.in.PurchaseTemporaryRequestVo;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Builder
@ToString
@Data
public class PurchaseTemporaryRequestDto {
    private double amount;
    private String memberUuid;

    public static PurchaseTemporaryRequestDto from(PurchaseTemporaryRequestVo purchaseTemporaryRequestVo,String memberUuid) {
        return PurchaseTemporaryRequestDto.builder()
                .memberUuid(memberUuid)
                .amount(purchaseTemporaryRequestVo.getAmount())
                .build();
    }

    public static String generateOrderCode() {
        String prefix = "O";
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuidPart = UUID.randomUUID().toString().substring(0, 8);

        return prefix + date + "-" + uuidPart;
    }
}
