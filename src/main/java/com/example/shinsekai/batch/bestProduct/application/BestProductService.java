package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.dto.out.BestProductResponseDto;
import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BestProductService {

    private final BestProductRepository bestProductRepository;

    public List<BestProductResponseDto> getProductRankByMainCategory(Long mainCategoryId) {
        return bestProductRepository.findByMainCategoryId(mainCategoryId).stream()
                .map(BestProductResponseDto::from)
                .toList();
    }
}
