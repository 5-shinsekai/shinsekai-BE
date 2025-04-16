package com.example.shinsekai.like.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.like.application.ProductLikeService;
import com.example.shinsekai.like.dto.in.ProductLikeRequestDto;
import com.example.shinsekai.like.dto.out.ProductLikeResponseDto;
import com.example.shinsekai.like.vo.in.ProductLikeRequestVo;
import com.example.shinsekai.like.vo.out.ProductLikeResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Like", description = "찜하기 관련 API")
@RequestMapping("/api/v1/product/like")
@RestController
@RequiredArgsConstructor
public class ProductLikeController {

    private final ProductLikeService productLikeService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "찜하기 조회")
    @GetMapping
    public BaseResponseEntity<List<ProductLikeResponseVo>> getLikeList() {
        List<ProductLikeResponseVo> result = productLikeService.getLikeList(jwtTokenProvider.getMemberUuid())
                .stream()
                .map(ProductLikeResponseDto::toVo)
                .toList();
        return new BaseResponseEntity<>(result);
    }

    @Operation(summary = "찜하기 토글")
    @PostMapping
    public BaseResponseEntity<Void> toggleLike(@RequestBody ProductLikeRequestVo productLikeRequestVo) {
        productLikeService.toggleLike(ProductLikeRequestDto.of(jwtTokenProvider.getMemberUuid(), productLikeRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
