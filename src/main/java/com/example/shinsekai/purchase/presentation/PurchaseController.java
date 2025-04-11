package com.example.shinsekai.purchase.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.purchase.application.PurchaseService;
import com.example.shinsekai.purchase.dto.in.PurchaseDeleteRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.example.shinsekai.purchase.vo.in.PurchaseRequestVo;
import com.example.shinsekai.purchase.vo.in.PurchaseTemporaryRequestVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.NestingKind;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final JwtTokenProvider jwtTokenProvider;

    //주문 번호 생성
    //스벅카드에서는 필요 없음
    @Operation(summary = "간편결제 이용 시 주문 번호 생성 후 레디스에 임시저장")
    @PostMapping("/orderId")
    public String createTemporaryPurchase(
            HttpServletRequest request,
            @RequestBody PurchaseTemporaryRequestVo purchaseTemporaryRequestVo) {
        return purchaseService.createTemporaryPurchase(
                PurchaseTemporaryRequestDto.from(purchaseTemporaryRequestVo, jwtTokenProvider.getAccessToken(request))
        );
    }

    @Operation(summary = "구매 정보 저장")
    @PostMapping()
    public BaseResponseEntity<Boolean> createPurchase(HttpServletRequest request,
                                                      @RequestBody PurchaseRequestVo purchaseRequestVo) {
        purchaseService.createPurchase(PurchaseRequestDto.from(purchaseRequestVo, jwtTokenProvider.getAccessToken(request))
                , purchaseRequestVo.getOrderProductList().stream().map(PurchaseProductListRequestDto::from).toList()
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "구매 환불")
    @PostMapping("/{purchaseCode}")
    public BaseResponseEntity<Boolean> cancelPurchase(HttpServletRequest request, @PathVariable String purchaseCode) {
        purchaseService.deletePurchase(PurchaseDeleteRequestDto.from(purchaseCode,jwtTokenProvider.getAccessToken(request)));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
