package com.example.shinsekai.event.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.event.dto.in.ProductEventListCreateRequestDto;
import com.example.shinsekai.event.dto.in.ProductEventListUpdateRequestDto;
import com.example.shinsekai.event.dto.out.ProductEventListGetResponseDto;
import com.example.shinsekai.event.entity.ProductEventList;
import com.example.shinsekai.event.infrastructure.ProductEventListRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductEventServiceImpl implements ProductEventService{

    private final ProductEventListRepository productEventListRepository;

    @Override
    public void createProductEventList(ProductEventListCreateRequestDto productEventListCreateRequestDto) {
        productEventListRepository.save(productEventListCreateRequestDto.toEntity());
    }

    @Override
    public List<ProductEventListGetResponseDto> getAllProductEventList(Integer eventId) {
        return productEventListRepository.findAllByEventId(eventId)
                .stream().map(ProductEventListGetResponseDto::from).toList();
    }

    @Override
    public void updateProductEventList(ProductEventListUpdateRequestDto productEventListUpdateRequestDto) {
        ProductEventList productEventList = productEventListRepository.findById(
                productEventListUpdateRequestDto.getId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_EVENT));

        productEventListRepository.save(productEventListUpdateRequestDto.toEntity(productEventList));
    }

    @Override
    public void deleteProductEventList(Long id) {
        ProductEventList productEventList = productEventListRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_EVENT));

        productEventListRepository.delete(productEventList);
    }

}