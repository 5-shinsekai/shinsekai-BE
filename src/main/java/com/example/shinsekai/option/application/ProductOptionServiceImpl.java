package com.example.shinsekai.option.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.dto.out.ProductOptionResponseDto;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.infrastructure.ColorRepository;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.option.infrastructure.SizeRepository;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionListRepository productOptionListRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductOptionResponseDto> getOptionsByProductCode(String productCode) {
        List<ProductOptionList> optionLists = productOptionListRepository.findByProductCode(productCode);
        return optionLists.stream()
                .map(ProductOptionResponseDto::from)
                .toList();
    }

    @Transactional
    @Override
    public void createOption(String productCode, ProductOptionRequestDto dto) {
        validateCreateOption(productCode, dto);
        productOptionListRepository.save(dto.toEntity(productCode));
    }

    @Transactional
    @Override
    public void deleteOption(Long id) {
        if (!productOptionListRepository.existsById(id)) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }
        productOptionListRepository.deleteById(id);
    }

    // 상품 옵션 생성 유효성 검사
    private void validateCreateOption(String productCode, ProductOptionRequestDto dto) {
        boolean exists = productOptionListRepository.existsByProductCodeAndColorIdAndSizeId(
                productCode,
                dto.getColorId(),
                dto.getSizeId()
        );
        if (exists) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_OPTION);
        }
        colorRepository.findById(dto.getColorId()).ifPresent(color -> {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        });
        sizeRepository.findById(dto.getSizeId()).ifPresent(size -> {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        });
    }
}
