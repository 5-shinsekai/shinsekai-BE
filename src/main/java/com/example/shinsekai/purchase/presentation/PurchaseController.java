package com.example.shinsekai.purchase.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.purchase.application.OrderService;
import com.example.shinsekai.purchase.application.PurchaseService;
import com.example.shinsekai.purchase.dto.in.CancelOrderRequestDto;
import com.example.shinsekai.purchase.dto.in.OrderRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.out.PurchaseResponseDto;
import com.example.shinsekai.purchase.vo.in.CancelOrderRequestVo;
import com.example.shinsekai.purchase.vo.in.OrderRequestVo;
import com.example.shinsekai.purchase.vo.out.PurchaseResponseVo;
import com.example.shinsekai.purchase.vo.out.PurchaseStatusResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Tag(name = "Purchase", description = "구매 관련 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
@RestController
public class PurchaseController {

    private final OrderService orderService;
    private final PurchaseService purchaseService;
    private final JwtTokenProvider jwtTokenProvider;


    @Operation(summary = "회원 구매 정보 조회")
    @GetMapping
    public List<PurchaseResponseVo> findPurchase() {
        return purchaseService.findMemberPurchaseList(jwtTokenProvider.getMemberUuid())
                .stream().map(PurchaseResponseDto::toVo).toList();
    }

    @Operation(summary = "회원별 주문/배송 현황 조회")
    @GetMapping("/status")
    public BaseResponseEntity<PurchaseStatusResponseVo> findPurchaseStatusByMemberUuid() {
        return new BaseResponseEntity<>(purchaseService.findPurchaseStatusByMemberUuid(jwtTokenProvider.getMemberUuid()).toVo());
    }

    @Operation(summary = "구매 정보 저장")
    @PostMapping
    public BaseResponseEntity<Boolean> createPurchase(@RequestBody OrderRequestVo orderRequestVo) {
        OrderRequestDto orderDto = OrderRequestDto.from(orderRequestVo, jwtTokenProvider.getMemberUuid());
        orderService.createOrder(orderDto, orderRequestVo.getOrderProductList().stream()
                .map(listVo -> PurchaseProductListRequestDto.from(listVo, orderDto.getPurchaseCode()))
                .toList()
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "구매 환불")
    @DeleteMapping
    public BaseResponseEntity<Boolean> cancelPurchase(@RequestBody CancelOrderRequestVo cancelOrderRequestVo) {
        orderService.deleteOrder(CancelOrderRequestDto.from(cancelOrderRequestVo, jwtTokenProvider.getMemberUuid()));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
