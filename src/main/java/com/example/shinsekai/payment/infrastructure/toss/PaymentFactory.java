package com.example.shinsekai.payment.infrastructure.toss;

import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class PaymentFactory {
    public Payment from(String paymentKey, JsonNode response, PurchaseTemporaryRequestDto temporaryPayment) {
        JsonNode cardNode = response.get("card");
        Payment.PaymentBuilder paymentBuilder = Payment.builder()
                .paymentCode(UUID.randomUUID().toString())
                .paymentKey(paymentKey)
                .memberUuid(temporaryPayment.getMemberUuid())
                .paymentMethod(response.get("method").asText())
                .paymentPrice(response.get("totalAmount").asDouble())
                .purchaseName(response.get("orderName").asText())
                .status(response.get("status").asText())
                .receiptUrl(response.get("receipt").get("url").asText())
                .approvedAt(OffsetDateTime.parse(response.get("approvedAt").asText()).toLocalDateTime());

        if (cardNode != null && !cardNode.isNull()) {
            paymentBuilder
                    .cardName(cardNode.get("company").asText(null))
                    .cardNumber(cardNode.get("number").asText(null))
                    .approveNo(cardNode.get("approveNo").asText(null))
                    .isInterestFree(cardNode.get("isInterestFree").asBoolean())
                    .installmentPlanMonths(cardNode.get("installmentPlanMonths").asInt())
                    .useCardPoint(cardNode.get("useCardPoint").asBoolean());
        }

        return paymentBuilder.build();
    }
}
