package com.example.shinsekai.like.application;

import com.example.shinsekai.like.dto.in.ProductLikeRequestDto;
import com.example.shinsekai.like.dto.out.ProductLikeResponseDto;
import com.example.shinsekai.like.infrastructure.ProductLikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLikeServiceImpl implements ProductLikeService {

    private final ProductLikeRepository productLikeRepository;

    @Override
    public List<ProductLikeResponseDto> getLikeList(String memberUuid) {
        return productLikeRepository.findByMemberUuid(memberUuid)
                .stream().map(ProductLikeResponseDto::from).toList();
    }

    @Override
    @Transactional
    public void toggleLike(ProductLikeRequestDto productLikeRequestDto) {
        if (productLikeRequestDto.getIsLiked()) {
            // 저장 로직
            productLikeRepository.save(productLikeRequestDto.toEntity());
        } else {
            // 삭제 로직
            productLikeRepository.delete(productLikeRequestDto.toEntity());
        }
    }
}
