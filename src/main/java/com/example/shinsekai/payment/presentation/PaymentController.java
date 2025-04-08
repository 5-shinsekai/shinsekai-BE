package com.example.shinsekai.payment.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.infrastructure.toss.TossPaymentClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final TossPaymentClient tossPaymentClient;

    @GetMapping("/success")
    public BaseResponseEntity<Boolean> paymentSuccess(
            @RequestParam String orderId,
            @RequestParam String paymentKey,
            @RequestParam int amount) {

        tossPaymentClient.processPaymentConfirmation(orderId, paymentKey, amount);
        return new BaseResponseEntity<>(true);
    }

}
