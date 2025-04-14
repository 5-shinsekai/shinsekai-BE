package com.example.shinsekai.purchase.application;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.purchase.dto.in.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService{

    private final PaymentService paymentService;
    private final StarbucksCardService starbucksCardService;
    private final PurchaseService purchaseService;

    @Override
    @Transactional
    public void createOrder(OrderRequestDto orderRequestDto, List<PurchaseProductListRequestDto> purchaseProductList) {
        //결제 paymentCode 있어야해
        String paymentCode = paymentService.createPayment(PaymentRequestDto.fromOrder(orderRequestDto));

        //스타벅스 카드 잔액 차감
        starbucksCardService.useRemainAmount(UseStarbucksCardRequestDto.builder()
                .memberStarbucksCardUuid(orderRequestDto.getMemberStarbucksCardUuid())
                .memberUuid(orderRequestDto.getMemberUuid())
                .price(orderRequestDto.getPaymentPrice())
                .build());

        //구매 productTotalPrice 는 상품의 전체 가격 ,paymentPrice 는 productTotalPrice+배송비 합한 전체 결제 가격
        purchaseService.createPurchase(PurchaseRequestDto.fromOrder(orderRequestDto, paymentCode), purchaseProductList);

        //재고 차감 -> 배치 할수잇으면 재고도 배치로...
    }

    @Override
    @Transactional
    public void deleteOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        //결제 취소
        paymentService.deletePayment(PaymentDeleteRequestDto.from(cancelOrderRequestDto.getPaymentCode(),cancelOrderRequestDto.getMemberUuid()));

        //스타벅스 잔액 충전

        //구매 취소

        //재고 충전?????
    }
}
