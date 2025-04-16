package com.example.shinsekai.season.application;

import com.example.shinsekai.season.dto.in.ProductSeasonListCreateRequestDto;
import com.example.shinsekai.season.dto.in.ProductSeasonListUpdateRequestDto;
import com.example.shinsekai.season.dto.out.ProductSeasonListGetResponseDto;
import com.example.shinsekai.season.entity.ProductSeasonList;

import java.util.List;

public interface ProductSeasonService {
    void createProductSeasonList(ProductSeasonListCreateRequestDto productSeasonListCreateRequestDto);
    List<ProductSeasonListGetResponseDto> getAllProductSeasonList(Integer seasonId);
    void updateProductSeasonList(ProductSeasonListUpdateRequestDto productSeasonListUpdateRequestDto);
    void deleteProductSeasonList(Long id);
}
