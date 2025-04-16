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

    private final OrderService orderService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(summary = "구매 정보 저장")
    @PostMapping
    public BaseResponseEntity<Boolean> createPurchase(HttpServletRequest request,
                                                      @RequestBody OrderRequestVo orderRequestVo) {

        OrderRequestDto orderDto = OrderRequestDto.from(orderRequestVo, jwtTokenProvider.getAccessToken(request));
        orderService.createOrder(orderDto, orderRequestVo.getOrderProductList().stream()
                        .map(listVo->PurchaseProductListRequestDto.from(listVo,orderDto.getPurchaseCode()))
                        .toList()
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
