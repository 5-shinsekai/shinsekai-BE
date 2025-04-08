package com.example.shinsekai.payment.infrastructure.toss;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TossPaymentClient {

    private final RedisProvider redisProvider;
    private final TossProperties tossProperties;
    private final PaymentFactory paymentFactory;
    private final PaymentService paymentService;

    //토스 페이먼츠 결제 인증 성공 시 정보 검증
    public void processPaymentConfirmation(String orderId, String paymentKey, double amount) {

        PurchaseTemporaryRequestDto temporaryPayment = redisProvider.getTemporaryPayment(orderId);
        if (temporaryPayment == null) {
            throw new BaseException(BaseResponseStatus.INVALID_ORDER_ID);
        }

        if (temporaryPayment.getAmount() != amount) {
            throw new BaseException(BaseResponseStatus.AMOUNT_MISMATCH);
        }

        confirmPayment(paymentKey, orderId, temporaryPayment);
    }


    //토스 페이먼츠 결제 승인 요청
    public void confirmPayment(String paymentKey, String orderId, PurchaseTemporaryRequestDto temporaryPayment) {
        try {
            String encodedAuth = Base64.getEncoder().encodeToString((tossProperties.getSecretKey() + ":").getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic " + encodedAuth);

            Map<String, Object> body = Map.of(
                    "paymentKey", paymentKey,
                    "orderId", orderId,
                    "amount", temporaryPayment.getAmount()
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(
                    tossProperties.getConfirmUrl(),
                    request,
                    String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new BaseException(BaseResponseStatus.PAYMENT_APPROVAL_FAILED);
            }

            //결제 저장
            paymentService.createPayment(paymentFactory.from(
                    paymentKey, new ObjectMapper().readTree(response.getBody()), temporaryPayment)
            );

        }catch (Exception e){
            //결제 취소 api(토스)
            cancelPayment();
            throw new BaseException(BaseResponseStatus.PAYMENT_APPROVAL_FAILED);
        }
    }

    public void cancelPayment() {}
}
