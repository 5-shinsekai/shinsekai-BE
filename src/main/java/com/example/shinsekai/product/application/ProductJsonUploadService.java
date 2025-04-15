package com.example.shinsekai.product.application;

import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.infrastructure.ProductCategoryListRepository;
import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@RequiredArgsConstructor
@Service
public class ProductJsonUploadService {

    private final ProductRepository productRepository;
    private final ProductCategoryListRepository productCategoryListRepository;
    private final ProductOptionListRepository productOptionListRepository;

    private static final Random random = new Random();

    private static long randomId() {
        return random.nextInt(5) + 1; // 1~5
    }

    private static int randomStock(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    @Transactional
    public void jsonUploadProduct(ProductRequestDto dto) {
        ProductResponseDto product = ProductResponseDto.from(productRepository.save(dto.toEntity()));
        String productCode = product.getProductCode();

        ProductCategoryList categoryList = ProductCategoryList.builder()
                .productCode(productCode)
                .mainCategoryId(randomId())
                .subCategoryId(randomId())
                .build();

        productCategoryListRepository.save(categoryList);

        ProductOptionList optionList = ProductOptionList.builder()
                .productCode(productCode)
                .colorId(randomId())
                .sizeId(randomId())
                .optionStatus(OptionStatus.IN_STOCK)
                .optionPrice(1000)
                .stockCount(randomStock(10, 100))
                .minStockCount(10)
                .build();

        productOptionListRepository.save(optionList);
    }
}
