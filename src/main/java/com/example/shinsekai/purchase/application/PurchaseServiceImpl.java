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

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductListRepository purchaseProductListRepository;

    @Override
    @Transactional
    public void createPurchase(PurchaseRequestDto purchaseRequestDto, List<PurchaseProductListRequestDto> purchaseProductListRequestDtoList) {
        purchaseRepository.save(purchaseRequestDto.toEntity());
        purchaseProductListRepository.saveAll(purchaseProductListRequestDtoList.stream()
                .map(PurchaseProductListRequestDto::toEntity).toList()
        );
    }

    @Override
    @Transactional
    public void deletePurchase(PurchaseDeleteRequestDto purchaseDeleteDto) {
        purchaseRepository.findByPurchaseCodeAndMemberUuid(purchaseDeleteDto.getPurchaseCode(), purchaseDeleteDto.getMemberUuid())
                .orElseThrow(()-> new BaseException(BaseResponseStatus.PURCHASE_NOT_FOUND)).cancelPurchase(purchaseDeleteDto.getCancelReason());
    }

    @Override
    public List<PurchaseProductList> findPurchaseProductListByPurchaseCode(String purchaseCode) {
        return purchaseProductListRepository.findByPurchaseCode(purchaseCode);
    }
}
