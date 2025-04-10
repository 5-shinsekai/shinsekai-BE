package com.example.shinsekai.card.presentation;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.MemberStarbucksListDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.vo.in.StarbucksCardRequestVo;
import com.example.shinsekai.card.vo.out.StarbucksCardResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "starbucksCard", description = "스타벅스 카드 API")
@RequestMapping("/api/v1/starbucks-card")
public class StarbucksCardController {

    private final StarbucksCardService starbucksCardService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "활성화된 스타벅스 카드 조회")
    @GetMapping//파라미터를 통해서 active 값에 대해 따라 값 다르게 들고 올 수 잇음
    public List<StarbucksCardResponseVo> getActiveStarbucksCards(HttpServletRequest request) {
        return starbucksCardService.getActiveStarbucksCards(jwtTokenProvider.getAccessToken(request)).stream()
                .map(StarbucksCardResponseDto::toVo)
                .toList();
    }

    @Operation(summary = "스타벅스 카드 등록")
    @PostMapping
    public BaseResponseEntity<Void> createStarbucksCard(HttpServletRequest request,
                                                        @RequestBody StarbucksCardRequestVo starbucksCardRequestVo) {
        starbucksCardService.createStarbucksCard(
                StarbucksCardRequestDto.from( starbucksCardRequestVo,jwtTokenProvider.getAccessToken(request))
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);

    }

    @Operation(summary = "스타벅스 카드 삭제 (비활성화만 가능)")
    @DeleteMapping("/{memberStarbucksCardUuid}")
    public BaseResponseEntity<Void> deleteStarbucksCard(HttpServletRequest request,
                                                        @PathVariable String memberStarbucksCardUuid) {
        starbucksCardService.deleteStarbucksCard(
                MemberStarbucksListDto.builder()
                        .memberStarbucksCardUuid(memberStarbucksCardUuid)
                        .memberUuid(jwtTokenProvider.getAccessToken(request))
                        .build());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
