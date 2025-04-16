package com.example.shinsekai.product.application;

import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.infrastructure.ProductCategoryListRepository;
import com.example.shinsekai.event.entity.ProductEventList;
import com.example.shinsekai.event.infrastructure.ProductEventListRepository;
import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.dto.in.ProductRequestDto;
import com.example.shinsekai.product.dto.out.ProductResponseDto;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import com.example.shinsekai.season.entity.ProductSeasonList;
import com.example.shinsekai.season.infrastructure.ProductSeasonListRepository;
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
    private final ProductEventListRepository productEventListRepository;
    private final ProductSeasonListRepository productSeasonListRepository;

    private static final Random random = new Random();

    private static long randomId() {
        return random.nextInt(5) + 1; // 1~5
    }

    private static long randomMainCategoryId() {
        return random.nextInt(7) + 1; // 1~7
    }

    private static Long randomSubCategoryIdFor(long mainCategoryId) {
        if (mainCategoryId == 1 || mainCategoryId == 2 || mainCategoryId == 5) {
            return (long) (random.nextInt(3) + 1); // 1~3
        }
        return null;
    }


    private static int randomStock(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    @Transactional
    public void jsonUploadProduct(ProductRequestDto dto) {
        ProductResponseDto product = ProductResponseDto.from(productRepository.save(dto.toEntity()));
        productRepository.flush();
        String productCode = product.getProductCode();
        long mainCategoryId = randomMainCategoryId();
        Long subCategoryId = randomSubCategoryIdFor(mainCategoryId);

        ProductCategoryList categoryList = ProductCategoryList.builder()
                .productCode(productCode)
                .mainCategoryId(mainCategoryId)
                .subCategoryId(subCategoryId)
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

        ProductEventList eventList = ProductEventList.builder()
                .productCode(productCode)
                .eventId(random.nextInt(6) + 1)
                .build();

        productEventListRepository.save(eventList);

        ProductSeasonList seasonList = ProductSeasonList.builder()
                .productCode(productCode)
                .seasonId(random.nextInt(6) + 1)
                .build();

        productSeasonListRepository.save(seasonList);
    }
}
