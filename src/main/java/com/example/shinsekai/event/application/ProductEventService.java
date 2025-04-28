package com.example.shinsekai.event.application;

import com.example.shinsekai.event.dto.in.ProductEventListCreateRequestDto;
import com.example.shinsekai.event.dto.in.ProductEventListUpdateRequestDto;
import com.example.shinsekai.event.dto.out.ProductEventListGetResponseDto;

import java.util.List;

public interface ProductEventService {
    void createProductEventList(ProductEventListCreateRequestDto productEventListCreateRequestDto);

    List<ProductEventListGetResponseDto> getAllProductEventList(Integer eventId);

    void updateProductEventList(ProductEventListUpdateRequestDto productEventListUpdateRequestDto);

    void deleteProductEventList(Long id);
}
