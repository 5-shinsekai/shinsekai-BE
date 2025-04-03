package com.example.shinsekai.purchase.presentation;

import com.example.shinsekai.purchase.application.PurchaseService;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/purchase")
@RestController
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping()
    public String createTemporaryPurchase(@RequestBody PurchaseTemporaryRequestDto purchaseTemporaryRequestDto) {
        return purchaseService.createTemporaryPurchase("member", purchaseTemporaryRequestDto);
    }
}
