package com.example.shinsekai.payment.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.dto.out.PaymentResponseDto;
import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.entity.PaymentStatus;
import com.example.shinsekai.payment.infrastructure.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto findAllPayment(String paymentCode, String memberUuid) {
        return PaymentResponseDto.from(paymentRepository.findByPaymentCodeAndMemberUuid(paymentCode, memberUuid)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_ORDER_ID)));
    }

    @Override
    @Transactional
    public String createPayment(PaymentRequestDto paymentRequestDto) {
        return paymentRepository.save(paymentRequestDto.toEntity()).getPaymentCode();
    }

    @Override
    @Transactional
    public void deletePayment(PaymentDeleteRequestDto paymentDeleteRequestDto) {
        Payment payment = paymentRepository.findByPaymentCodeAndMemberUuidAndStatus(
                        paymentDeleteRequestDto.getPaymentCode(), paymentDeleteRequestDto.getMemberUuid(), PaymentStatus.DONE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_ORDER_ID));

        if (!Objects.equals(payment.getPaymentPrice(), paymentDeleteRequestDto.getRefundAmount())) {
            throw new BaseException(BaseResponseStatus.AMOUNT_MISMATCH);
        }

        payment.cancelPayment();
    }
}
