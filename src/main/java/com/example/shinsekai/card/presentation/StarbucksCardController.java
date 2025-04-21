package com.example.shinsekai.card.presentation;

import com.example.shinsekai.card.application.StarbucksCardService;
import com.example.shinsekai.card.dto.in.MemberStarbucksListDto;
import com.example.shinsekai.card.dto.in.StarbucksCardRequestDto;
import com.example.shinsekai.card.dto.in.TransferStarbucksCardDto;
import com.example.shinsekai.card.dto.in.UseStarbucksCardRequestDto;
import com.example.shinsekai.card.dto.out.StarbucksCardResponseDto;
import com.example.shinsekai.card.vo.in.ChargeStarbucksCardVo;
import com.example.shinsekai.card.vo.in.StarbucksCardRequestVo;
import com.example.shinsekai.card.vo.in.TransferStarbucksCardVo;
import com.example.shinsekai.card.vo.out.StarbucksCardResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @GetMapping
    public List<StarbucksCardResponseVo> getActiveStarbucksCards() {
        return starbucksCardService.getActiveStarbucksCards(jwtTokenProvider.getMemberUuid()).stream()
                .map(StarbucksCardResponseDto::toVo)
                .toList();
    }

    @Operation(summary = "스타벅스 카드 단일 조회")
    @GetMapping("/{memberStarbucksCardUuid}")
    public StarbucksCardResponseVo getStarbucksCard(@PathVariable String memberStarbucksCardUuid) {
        return starbucksCardService.getStarbucksCard(
                MemberStarbucksListDto.builder()
                        .memberStarbucksCardUuid(memberStarbucksCardUuid)
                        .memberUuid(jwtTokenProvider.getMemberUuid())
                        .build()
        ).toVo();
    }

    @Operation(summary = "스타벅스 카드 등록")
    @PostMapping
    public BaseResponseEntity<Void> createStarbucksCard(@RequestBody StarbucksCardRequestVo starbucksCardRequestVo) {
        starbucksCardService.createStarbucksCard(
                StarbucksCardRequestDto.from( starbucksCardRequestVo,jwtTokenProvider.getMemberUuid())
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "스타벅스 카드 삭제", description = "비활성화만 가능")
    @DeleteMapping("/{memberStarbucksCardUuid}")
    public BaseResponseEntity<Void> deleteStarbucksCard(@PathVariable String memberStarbucksCardUuid) {
        starbucksCardService.deleteStarbucksCard(
                MemberStarbucksListDto.builder()
                        .memberStarbucksCardUuid(memberStarbucksCardUuid)
                        .memberUuid(jwtTokenProvider.getMemberUuid())
                        .build());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "스타벅스 카드 충전")
    @PutMapping("/charge")
    public  BaseResponseEntity<Void> chargeStarbucksCard(@RequestBody ChargeStarbucksCardVo starbucksCardRequestVo) {
        starbucksCardService.chargeRemainAmount(
                UseStarbucksCardRequestDto.builder()
                        .memberStarbucksCardUuid(starbucksCardRequestVo.getMemberStarbucksCardUuid())
                        .memberUuid(jwtTokenProvider.getMemberUuid())
                        .price(starbucksCardRequestVo.getPrice())
                        .build());
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "스타벅스 카드 잔액 이전")
    @PostMapping("/transfer")
    public BaseResponseEntity<Void> transferStarbucksCardRemainAmount(@RequestBody TransferStarbucksCardVo starbucksCardRequestVo) {
        starbucksCardService.transferRemainAmount(
                TransferStarbucksCardDto.from(starbucksCardRequestVo, jwtTokenProvider.getMemberUuid())
        );
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
