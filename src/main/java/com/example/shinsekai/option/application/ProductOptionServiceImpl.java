package com.example.shinsekai.option.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.option.dto.in.ProductOptionRequestDto;
import com.example.shinsekai.option.entity.Color;
import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.entity.Size;
import com.example.shinsekai.option.infrastructure.ColorRepository;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.option.infrastructure.SizeRepository;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductOptionServiceImpl implements ProductOptionService {

    private final ProductOptionListRepository productOptionListRepository;
    private final SizeRepository sizeRepository;
    private final ColorRepository colorRepository;
    private final ProductRepository productRepository;

    /*@Transactional
    @Override
    public void createOption(String productCode, ProductOptionRequestDto dto) {
        Product product = productRepository.findByProductCode(productCode)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));
        Size size = sizeRepository.findById(dto.getSizeId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));
        Color color = colorRepository.findById(dto.getColorId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

        ProductOptionList option = ProductOptionList.builder()

    }*/
}
