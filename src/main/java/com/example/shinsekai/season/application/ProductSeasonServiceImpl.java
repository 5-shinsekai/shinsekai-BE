package com.example.shinsekai.season.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.season.dto.in.ProductSeasonListCreateRequestDto;
import com.example.shinsekai.season.dto.in.ProductSeasonListUpdateRequestDto;
import com.example.shinsekai.season.dto.out.ProductSeasonListGetResponseDto;
import com.example.shinsekai.season.entity.ProductSeasonList;
import com.example.shinsekai.season.infrastructure.ProductSeasonListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductSeasonServiceImpl implements ProductSeasonService {

    private final ProductSeasonListRepository productSeasonListRepository;

    @Override
    public void createProductSeasonList(ProductSeasonListCreateRequestDto productSeasonListCreateRequestDto) {
        productSeasonListRepository.save(productSeasonListCreateRequestDto.toEntity());
    }

    @Override
    public List<ProductSeasonListGetResponseDto> getAllProductSeasonList(Integer seasonId) {
        return productSeasonListRepository.findAllBySeasonId(seasonId)
                .stream().map(ProductSeasonListGetResponseDto::from).toList();
    }

    @Override
    public void updateProductSeasonList(ProductSeasonListUpdateRequestDto productSeasonListUpdateRequestDto) {
        ProductSeasonList productSeasonList = productSeasonListRepository.findById(
                productSeasonListUpdateRequestDto.getId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_SEASON));

        productSeasonListRepository.save(productSeasonListUpdateRequestDto.toEntity(productSeasonList));
    }

    @Override
    public void deleteProductSeasonList(Long id) {
        ProductSeasonList productSeasonList = productSeasonListRepository.findById(id)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT_SEASON));

        productSeasonListRepository.delete(productSeasonList);
    }
}
