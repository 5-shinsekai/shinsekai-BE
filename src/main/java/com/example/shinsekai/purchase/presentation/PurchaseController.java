package com.example.shinsekai.purchase.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.purchase.application.PurchaseService;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.example.shinsekai.purchase.vo.in.PurchaseRequestVo;
import com.example.shinsekai.purchase.vo.in.PurchaseTemporaryRequestVo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/orderId")
    public String createTemporaryPurchase(
            HttpServletRequest request,
            @RequestBody PurchaseTemporaryRequestVo purchaseTemporaryRequestVo) {
        return purchaseService.createTemporaryPurchase(
                request.getHeader("Authorization").substring(7), PurchaseTemporaryRequestDto.from(purchaseTemporaryRequestVo)
        );
    }

    @PostMapping()
    public BaseResponseEntity<Boolean> createPurchase(HttpServletRequest request,
                                                      @RequestBody PurchaseRequestVo purchaseRequestVo) {
        purchaseService.createPurchase(request.getHeader("Authorization").substring(7), PurchaseRequestDto.from(purchaseRequestVo)
                , purchaseRequestVo.getOrderProductList().stream().map(PurchaseProductListRequestDto::from).toList()
        );
        return new BaseResponseEntity<>(true);
    }
}
