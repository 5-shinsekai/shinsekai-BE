package com.example.shinsekai.purchase.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.purchase.application.OrderService;
import com.example.shinsekai.purchase.application.PurchaseService;
import com.example.shinsekai.purchase.dto.in.*;
import com.example.shinsekai.purchase.vo.in.CancelOrderRequestVo;
import com.example.shinsekai.purchase.vo.in.OrderRequestVo;
import com.example.shinsekai.purchase.vo.in.PurchaseRequestVo;
import com.example.shinsekai.purchase.vo.in.PurchaseTemporaryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;

    //Todo: 간편결제 의논 후 스타벅스카드만 사용시 주문번호 자동생성 위치 변경 및 api 삭제
    @Operation(summary = "주문 번호 생성", description = "간편결제 이용 시 주문 번호 생성 후 레디스에 임시저장/ 스벅카드만 사용 시 필요없음요....")
    @PostMapping("/orderId")
    public String createTemporaryPurchase(
            HttpServletRequest request,
            @RequestBody PurchaseTemporaryRequestVo purchaseTemporaryRequestVo) {
        return purchaseService.createTemporaryPurchase(
                PurchaseTemporaryRequestDto.from(purchaseTemporaryRequestVo, jwtTokenProvider.getAccessToken(request))
        );
    }

//    @Operation(summary = "구매 정보 저장")
//    @PostMapping()
//    public BaseResponseEntity<Boolean> createPurchase(HttpServletRequest request,
//                                                      @RequestBody PurchaseRequestVo purchaseRequestVo) {
//        purchaseService.createPurchase(PurchaseRequestDto.from(purchaseRequestVo, jwtTokenProvider.getAccessToken(request))
//                , purchaseRequestVo.getOrderProductList().stream().map(PurchaseProductListRequestDto::from).toList()
//        );
//        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
//    }

    @Operation(summary = "구매 정보 저장")
    @PostMapping
    public BaseResponseEntity<Boolean> createPurchase(HttpServletRequest request,
                                                      @RequestBody OrderRequestVo orderRequestVo) {

        log.info("OrderRequestVo: {}", orderRequestVo);
        orderService.createOrder(OrderRequestDto.from(orderRequestVo, jwtTokenProvider.getAccessToken(request))
                , orderRequestVo.getOrderProductList().stream().map(PurchaseProductListRequestDto::from).toList()
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }



    @Operation(summary = "구매 환불")
    @DeleteMapping
    public BaseResponseEntity<Boolean> cancelPurchase(HttpServletRequest request,
                                                      @RequestBody CancelOrderRequestVo cancelOrderRequestVo) {
        orderService.deleteOrder(CancelOrderRequestDto.from(cancelOrderRequestVo,jwtTokenProvider.getAccessToken(request)));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
