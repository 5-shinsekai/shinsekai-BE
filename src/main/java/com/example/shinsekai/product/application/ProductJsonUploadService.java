package com.example.shinsekai.product.application;

import com.example.shinsekai.category.entity.MainCategory;
import com.example.shinsekai.category.entity.ProductCategoryList;
import com.example.shinsekai.category.entity.SubCategory;
import com.example.shinsekai.category.infrastructure.MainCategoryRepository;
import com.example.shinsekai.category.infrastructure.ProductCategoryListRepository;
import com.example.shinsekai.category.infrastructure.SubCategoryRepository;
import com.example.shinsekai.common.exception.BaseException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductJsonUploadService {

    private final ProductRepository productRepository;
    private final ProductCategoryListRepository productCategoryListRepository;
    private final ProductOptionListRepository productOptionListRepository;
    private final ProductEventListRepository productEventListRepository;
    private final ProductSeasonListRepository productSeasonListRepository;
    private final MainCategoryRepository mainCategoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    private static final Random random = new Random();

    private static long randomId() {
        return random.nextInt(5) + 1; // 1~5
    }

    private static int randomStock(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private Long findOrCreateMainCategory(String name) {
        return mainCategoryRepository.findByName(name)
                .orElseGet(() -> mainCategoryRepository.save(
                        MainCategory.builder()
                                .name(name)
                                .categoryImage("/images/category/default.png")
                                .categoryImageAltText(name + " 이미지")
                                .build()
                )).getId();
    }

    private Long findOrCreateSubCategory(String name, Long mainCategoryId) {
        return subCategoryRepository.findByNameAndMainCategoryId(name, mainCategoryId)
                .orElseGet(() -> subCategoryRepository.save(
                        SubCategory.builder()
                                .name(name)
                                .mainCategoryId(mainCategoryId)
                                .build()
                )).getId();
    }


    @Transactional
    public void jsonUploadProduct(ProductRequestDto dto, Map<String, Object> rawJson) {
        ProductResponseDto product = ProductResponseDto.from(productRepository.save(dto.toEntity()));
        productRepository.flush();
        String productCode = product.getProductCode();

        List<String> categoryNames = (List<String>) rawJson.get("카테고리");
        String mainCategoryName = categoryNames.get(0);
        String subCategoryName = categoryNames.size() > 1 ? categoryNames.get(1) : null;

        long mainCategoryId = findOrCreateMainCategory(mainCategoryName);
        Long subCategoryId = (subCategoryName != null)
                ? findOrCreateSubCategory(subCategoryName, mainCategoryId)
                : null;

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

    @Transactional
    public void jsonUploadPartOfProduct(ProductRequestDto dto, Map<String, Object> rawJson) {
        ProductResponseDto product = ProductResponseDto.from(productRepository.save(dto.toEntity()));
        productRepository.flush();
        String productCode = product.getProductCode();

        List<String> categoryNames = (List<String>) rawJson.get("카테고리");
        String mainCategoryName = categoryNames.get(0);
        String subCategoryName = categoryNames.size() > 1 ? categoryNames.get(1) : null;

        long mainCategoryId = findOrCreateMainCategory(mainCategoryName);
        Long subCategoryId = (subCategoryName != null)
                ? findOrCreateSubCategory(subCategoryName, mainCategoryId)
                : null;

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
    }
}
