package com.example.shinsekai.purchase.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.common.redis.RedisProvider;
import com.example.shinsekai.purchase.dto.in.PurchaseDeleteRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto;
import com.example.shinsekai.purchase.entity.PurchaseProductList;
import com.example.shinsekai.purchase.infrastructure.PurchaseProductListRepository;
import com.example.shinsekai.purchase.infrastructure.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.shinsekai.purchase.dto.in.PurchaseTemporaryRequestDto.generateOrderCode;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final RedisProvider redisProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductListRepository purchaseProductListRepository;

    @Override
    public String createTemporaryPurchase(PurchaseTemporaryRequestDto purchaseTemporaryRequestDto) {
        return redisProvider.setTemporaryPayment(generateOrderCode(),purchaseTemporaryRequestDto,10);
    }

    @Override
    @Transactional
    public void createPurchase(PurchaseRequestDto purchaseRequestDto, List<PurchaseProductListRequestDto> purchaseProductListRequestDtoList) {
        try {
            purchaseRepository.save(purchaseRequestDto.toEntity());
            purchaseProductListRepository.saveAll(purchaseProductListRequestDtoList.stream()
                    .map(PurchaseProductListRequestDto::toEntity).toList()
            );
        }catch (Exception e){
            //결제 취소

            throw new BaseException(BaseResponseStatus.PURCHASE_CREATION_FAILED);
        }
    }

    @Override
    @Transactional
    public void deletePurchase(PurchaseDeleteRequestDto purchaseDeleteRequestDto) {

    }
}
