package com.example.shinsekai.payment.presentation;

import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.vo.out.PaymentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "결제 관련 API")
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "결제 조회")
    @GetMapping("/{paymentCode}")
    public PaymentResponseVo findAllPayment(HttpServletRequest request, @PathVariable String paymentCode) {
        return paymentService.findAllPayment(paymentCode, jwtTokenProvider.getAccessToken(request)).toVo();
    }
}
