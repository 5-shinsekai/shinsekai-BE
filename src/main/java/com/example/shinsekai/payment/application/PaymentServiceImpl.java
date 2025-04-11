package com.example.shinsekai.payment.application;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.payment.entity.Payment;
import com.example.shinsekai.payment.infrastructure.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StarbucksCardService starbucksCardService;

    @Override
    @Transactional
    public void createPayment(PaymentRequestDto paymentRequestDto) {
        paymentRepository.save(paymentRequestDto.toEntity());

        //스타벅스 카드 결제
        if(paymentRequestDto.getMemberStarbucksCardUuid() != null){
            starbucksCardService.useRemainAmount(UseStarbucksCardRequestDto.builder()
                    .memberStarbucksCardUuid(paymentRequestDto.getMemberStarbucksCardUuid())
                    .memberUuid(paymentRequestDto.getMemberUuid())
                    .price(paymentRequestDto.getPaymentPrice())
                    .build());
        }
    }

    @Override
    @Transactional
    public void deletePayment(PaymentDeleteRequestDto paymentDeleteRequestDto) {
        Payment payment = paymentRepository.findByPaymentCodeAndMemberUuid(
                paymentDeleteRequestDto.getPaymentCode(), paymentDeleteRequestDto.getMemberUuid()
        );
        payment.cancelPayment();

        //Starbucks 카드 결제 취소(잔여금액 ++)
    }
}
