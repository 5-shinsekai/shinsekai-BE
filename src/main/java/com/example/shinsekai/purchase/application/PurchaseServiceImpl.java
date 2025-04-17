package com.example.shinsekai.purchase.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.purchase.dto.in.PurchaseDeleteRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseProductListRequestDto;
import com.example.shinsekai.purchase.dto.in.PurchaseRequestDto;
import com.example.shinsekai.purchase.dto.out.PurchaseProductListResponseDto;
import com.example.shinsekai.purchase.dto.out.PurchaseResponseDto;
import com.example.shinsekai.purchase.infrastructure.PurchaseProductListRepository;
import com.example.shinsekai.purchase.infrastructure.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseProductListRepository purchaseProductListRepository;

    @Override
    @Transactional
    public void createPurchase(PurchaseRequestDto purchaseRequestDto, List<PurchaseProductListRequestDto> purchaseProductListRequestDtoList) {
        purchaseRepository.save(purchaseRequestDto.toEntity());
        purchaseProductListRepository.saveAll(purchaseProductListRequestDtoList
                .stream()
                .map(PurchaseProductListRequestDto::toEntity).toList()
        );
    }

    @Override
    @Transactional
    public void deletePurchase(PurchaseDeleteRequestDto purchaseDeleteDto) {
        purchaseRepository.findByPurchaseCodeAndMemberUuid(purchaseDeleteDto.getPurchaseCode(), purchaseDeleteDto.getMemberUuid())
                .orElseThrow(()-> new BaseException(BaseResponseStatus.PURCHASE_NOT_FOUND))
                .cancelPurchase(purchaseDeleteDto.getCancelReason());
    }

    //회원의 주문 목록과 해당 주문의 상품들 조회
    @Override
    public List<PurchaseResponseDto> findMemberPurchaseList(String memberUuid) {
        return purchaseRepository.findByMemberUuid(memberUuid)
                .stream()
                .map(purchase -> {
                    List<PurchaseProductListResponseDto> productList =
                            findPurchaseProductListByPurchaseCode(purchase.getPurchaseCode());
                    return PurchaseResponseDto.from(purchase, productList);
                })
                .toList();
    }

    @Override
    public List<PurchaseProductListResponseDto> findPurchaseProductListByPurchaseCode(String purchaseCode) {
        return purchaseProductListRepository.findByPurchaseCode(purchaseCode)
                .stream()
                .map(PurchaseProductListResponseDto::from)
                .toList();
    }
}
