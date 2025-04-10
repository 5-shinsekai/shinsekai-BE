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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionListRepository productOptionListRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;

    @Override
    public ProductOptionResponseDto getProductOption(Long productOptionId) {
        ProductOptionList optionLists = productOptionListRepository.findById(productOptionId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));
        return ProductOptionResponseDto.from(optionLists);
    }

    @Override
    @Transactional
    public void createOption(ProductOptionRequestDto dto) {
        validateCreateOption(dto);
        productOptionListRepository.save(dto.toEntity());
    }

    @Override
    @Transactional
    public void deleteOption(Long id) {
        if (!productOptionListRepository.existsById(id)) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }
        productOptionListRepository.deleteById(id);
    }

    // 상품 옵션 생성 유효성 검사
    private void validateCreateOption(ProductOptionRequestDto dto) {
        boolean exists = productOptionListRepository.existsByProductCodeAndColorIdAndSizeId(
                dto.getProductCode(),
                dto.getColorId(),
                dto.getSizeId()
        );
        if (exists) {
            throw new BaseException(BaseResponseStatus.DUPLICATED_OPTION);
        }
        if (!colorRepository.existsById(dto.getColorId())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }
        if (!sizeRepository.existsById(dto.getSizeId())) {
            throw new BaseException(BaseResponseStatus.NO_EXIST_OPTION);
        }
    }
}
