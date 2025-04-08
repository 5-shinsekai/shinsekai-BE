package com.example.shinsekai.payment.application;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.infrastructure.PaymentRepository;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public void createPayment(Payment payment) {
        try{
            paymentRepository.save(payment);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.PAYMENT_FAILED);
        }
    }

    @Override
    public void deletePayment(PaymentRequestDto paymentRequestDto) {

    }
}
