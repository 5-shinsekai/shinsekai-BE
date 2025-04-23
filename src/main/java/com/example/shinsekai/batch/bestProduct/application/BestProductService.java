package com.example.shinsekai.batch.bestProduct.application;

import com.example.shinsekai.batch.bestProduct.infrastructure.BestProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BestProductService {

    private final BestProductRepository bestProductRepository;

    public List<String> getProductRankByMainCategory(Long mainCategoryId) {
        return bestProductRepository.findProductCodesByMainCategoryOrderedByRank(mainCategoryId);
    }
}
