package com.example.shinsekai.purchase.application;

import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final RedisProvider redisProvider;

    @Override
    public String createTemporaryPurchase(String memberUuid, PurchaseTemporaryRequestDto purchaseTemporaryRequestDto) {
        String orderUuid = "order" + UUID.randomUUID().toString();
        redisProvider.setTemporaryPayment(orderUuid,purchaseTemporaryRequestDto,100000);

        return orderUuid;
    }

    @Override
    public boolean validatePurchase(String purchaseUuid, double amount) {
        return false;
    }

    @Override
    public void createPurchase(PurchaseRequestDto purchaseRequestDto) {

    }
}
