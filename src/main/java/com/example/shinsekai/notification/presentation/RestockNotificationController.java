package com.example.shinsekai.notification.presentation;

import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.jwt.JwtTokenProvider;
import com.example.shinsekai.notification.application.RestockNotificationService;
import com.example.shinsekai.notification.dto.in.RestockNotificationRequestDto;
import com.example.shinsekai.notification.dto.out.RestockNotificationResponseDto;
import com.example.shinsekai.notification.vo.in.RestockNotificationRequestVo;
import com.example.shinsekai.notification.vo.out.RestockNotificationResponseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Restock", description = "재입고 알림 API")
@RestController
@RequestMapping("/api/v1/restock")
@RequiredArgsConstructor
public class RestockNotificationController {

    private final RestockNotificationService restockNotificationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "회원별 알림내역 조회")
    @GetMapping("/find")
    public BaseResponseEntity<List<RestockNotificationResponseVo>> findAll() {
        return new BaseResponseEntity<>(restockNotificationService.findMyMemberUuid(jwtTokenProvider.getMemberUuid())
                .stream()
                .map(RestockNotificationResponseDto::toVo)
                .toList());
    }


    @Operation(summary = "재입고 알림 신청")
    @PostMapping("/notify")
    public BaseResponseEntity<Void> requestRestock(@RequestBody RestockNotificationRequestVo vo) {
        String memberUuid = jwtTokenProvider.getMemberUuid();
        restockNotificationService.register(memberUuid, RestockNotificationRequestDto.from(vo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
