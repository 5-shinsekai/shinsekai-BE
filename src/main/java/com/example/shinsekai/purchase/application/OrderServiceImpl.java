package com.example.shinsekai.purchase.application;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.option.application.ProductOptionService;
import com.example.shinsekai.payment.application.PaymentService;
import com.example.shinsekai.payment.dto.in.PaymentDeleteRequestDto;
import com.example.shinsekai.payment.dto.in.PaymentRequestDto;
import com.example.shinsekai.purchase.dto.in.*;
import com.example.shinsekai.purchase.entity.PurchaseProductList;
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
    private final ProductOptionService productOptionService;

    @Override
    @Transactional
    public void createOrder(OrderRequestDto orderRequestDto, List<PurchaseProductListRequestDto> purchaseProductList) {
        //결제
        String paymentCode = paymentService.createPayment(PaymentRequestDto.fromOrder(orderRequestDto));

        //스타벅스 카드 잔액 차감
        starbucksCardService.useRemainAmount(UseStarbucksCardRequestDto.builder()
                .memberStarbucksCardUuid(orderRequestDto.getMemberStarbucksCardUuid())
                .memberUuid(orderRequestDto.getMemberUuid())
                .price(orderRequestDto.getPaymentPrice())
                .build());

        //구매
        //productTotalPrice 는 상품의 전체 가격 ,paymentPrice 는 productTotalPrice+배송비 합한 전체 결제 가격
        purchaseService.createPurchase(PurchaseRequestDto.fromOrder(orderRequestDto, paymentCode), purchaseProductList);

        //재고 차감
        /*Todo: 재고는 실시간 관리가 어렵다고 함 (동시성 등).
        *  배치를 사용해 한번에 재고 처리를 할 수도 있지만 이번 1차 플젝에서는 일단 제외
         */
        purchaseProductList.forEach(dto
                -> productOptionService.decreaseOptionStock(dto.getProductOptionId(), dto.getQuantity())
        );

    }

    @Override
    @Transactional
    public void deleteOrder(CancelOrderRequestDto cancelOrderRequestDto) {
        //결제 취소
        paymentService.deletePayment(PaymentDeleteRequestDto.from(cancelOrderRequestDto.getPaymentCode()
                , cancelOrderRequestDto.getRefundAmount(),cancelOrderRequestDto.getMemberUuid()));

        //스타벅스 잔액 충전
        starbucksCardService.useRemainAmount(UseStarbucksCardRequestDto.builder()
                .memberStarbucksCardUuid(cancelOrderRequestDto.getMemberStarbucksCardUuid())
                .memberUuid(cancelOrderRequestDto.getMemberUuid())
                .price(cancelOrderRequestDto.getRefundAmount() * -1)
                .build());

        //구매 취소
        purchaseService.deletePurchase(PurchaseDeleteRequestDto.from(cancelOrderRequestDto.getPurchaseCode()
                , cancelOrderRequestDto.getCancelReason(), cancelOrderRequestDto.getMemberUuid()));

        //재고 충전
        purchaseService.findPurchaseProductListByPurchaseCode(cancelOrderRequestDto.getPurchaseCode())
                .forEach(entity -> {
                    System.out.println(entity);
                    productOptionService.increaseOptionStock(entity.getProductOptionId(), entity.getQuantity());
                }); 
    }
}
