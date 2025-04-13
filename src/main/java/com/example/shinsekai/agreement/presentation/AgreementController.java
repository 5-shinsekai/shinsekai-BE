package com.example.shinsekai.agreement.presentation;

import com.example.shinsekai.agreement.application.AgreementService;
import com.example.shinsekai.agreement.dto.in.AgreementCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.AgreementUpdateRequestDto;
import com.example.shinsekai.agreement.dto.out.AgreementResponseDto;
import com.example.shinsekai.agreement.vo.in.AgreementCreateRequestVo;
import com.example.shinsekai.agreement.vo.in.AgreementUpdateRequestVo;
import com.example.shinsekai.agreement.vo.out.AgreementResponseVo;
import com.example.shinsekai.common.entity.BaseResponseEntity;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/agreement")
@RestController
@RequiredArgsConstructor
@Slf4j
public class AgreementController {

    private final AgreementService agreementService;

    @Operation(summary = "약관 전체 조회")
    @GetMapping
    public BaseResponseEntity<List<AgreementResponseVo>> getAllAgreements() {
        return new BaseResponseEntity<>(
                agreementService.getAllAgreements()
                        .stream()
                        .map(AgreementResponseDto::toVo)
                        .toList());
    }

    @Operation(summary = "약관 단건 조회")
    @GetMapping("/{agreementId}")
    public BaseResponseEntity<AgreementResponseVo> getAgreement(@PathVariable Long agreementId) {
        return new BaseResponseEntity<>(AgreementResponseDto.toVo(agreementService.getAgreement(agreementId)));
    }

    @Operation(summary = "약관 생성")
    @PostMapping
    public BaseResponseEntity<Void> createAgreement(@RequestBody AgreementCreateRequestVo agreementCreateRequestVo) {
        agreementService.createAgreement(AgreementCreateRequestDto.from(agreementCreateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "약관 수정")
    @PutMapping
    public BaseResponseEntity<Void> updateAgreement(@Valid @RequestBody AgreementUpdateRequestVo
                                                                agreementUpdateRequestVo) {
        agreementService.updateAgreement(AgreementUpdateRequestDto.from(agreementUpdateRequestVo));
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }

    @Operation(summary = "약관 삭제")
    @DeleteMapping("/{agreementId}")
    public BaseResponseEntity<Void> deleteAgreement(@PathVariable Long agreementId) {
        agreementService.deleteAgreement(agreementId);
        return new BaseResponseEntity<>(BaseResponseStatus.SUCCESS);
    }
}
