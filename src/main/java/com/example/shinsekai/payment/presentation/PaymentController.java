package com.example.shinsekai.payment.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.vo.in.PaymentRequestVo;
import com.example.shinsekai.payment.vo.out.PaymentResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final PaymentService paymentService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "결제 조회")
    @GetMapping("/{paymentCode}")
    public PaymentResponseVo findPayment(HttpServletRequest request, @PathVariable String paymentCode){
        return paymentService.findPayment(paymentCode, jwtTokenProvider.getAccessToken(request)).toVo();
    }

//    @Operation(summary = "결제 API" , description = "간편결제 / 스타벅스 카드 결제")
//    @PostMapping
//    public BaseResponseEntity<Void> createPayment(HttpServletRequest request,
//                                                  @RequestBody PaymentRequestVo paymentRequestVo) {
//        paymentService.createPayment(
//                PaymentRequestDto.from(paymentRequestVo, jwtTokenProvider.getAccessToken(request) )
//        );
//        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
//    }
//
//    @Operation(summary = "결제 취소 API")
//    @DeleteMapping
//    public BaseResponseEntity<Void> deletePayment(HttpServletRequest request,
//                                                  @RequestBody PaymentRequestVo paymentRequestVo) {
//        paymentService.deletePayment(PaymentRequestDto.from(paymentRequestVo, jwtTokenProvider.getAccessToken(request)));
//        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
//    }
}
