package com.example.shinsekai.event.application;

import com.example.shinsekai.event.dto.in.ProductEventListCreateRequestDto;
import com.example.shinsekai.event.dto.in.ProductEventListUpdateRequestDto;
import com.example.shinsekai.event.dto.out.ProductEventListGetResponseDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@NoArgsConstructor
@Service
public class ProductEventServiceImpl implements ProductEventService{
    @Override
    public void createProductEventList(ProductEventListCreateRequestDto productEventListCreateRequestDto) {

    }

    @Override
    public List<ProductEventListGetResponseDto> getAllProductEventList(Integer eventId) {
        return List.of();
    }

    @Override
    public void updateProductEventList(ProductEventListUpdateRequestDto productEventListUpdateRequestDto) {

    }

    @Override
    public void deleteProductEventList(Long id) {

    }
}
