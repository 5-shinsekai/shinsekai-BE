package com.example.shinsekai.agreement.application;

import com.example.shinsekai.agreement.dto.in.AgreementCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.AgreementUpdateRequestDto;
import com.example.shinsekai.agreement.dto.in.MemberAgreementListCreateRequestDto;
import com.example.shinsekai.agreement.dto.in.MemberAgreementListUpdateRequestDto;
import com.example.shinsekai.agreement.dto.out.AgreementResponseDto;
import com.example.shinsekai.agreement.entity.Agreement;
import com.example.shinsekai.agreement.entity.MemberAgreementList;
import com.example.shinsekai.agreement.infrastructure.AgreementRepository;
import com.example.shinsekai.agreement.infrastructure.MemberAgreementListRepository;
import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AgreementServiceImpl implements AgreementService{

    private final AgreementRepository agreementRepository;
    private final MemberAgreementListRepository memberAgreementListRepository;

    @Override
    public List<AgreementResponseDto> getAllAgreement() {
        return agreementRepository.findAll()
                .stream()
                .map(AgreementResponseDto::from)
                .filter(agreement -> agreement.getStoredExpiredDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public AgreementResponseDto getAgreement(Long agreementId) {
        Agreement agreement = agreementRepository.findById(agreementId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));
        return AgreementResponseDto.from(agreement);
    }

    @Override
    @Transactional
    public void createAgreement(AgreementCreateRequestDto agreementCreateRequestDto) {
        agreementRepository.save(agreementCreateRequestDto.toEntity());
    }

    @Override
    @Transactional
    public void updateAgreement(AgreementUpdateRequestDto agreementUpdateRequestDto) {
        Agreement agreement = agreementRepository.findById(agreementUpdateRequestDto.getAgreementId()).orElseThrow(
                () -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));
        agreementRepository.save(agreementUpdateRequestDto.toEntity(agreement));
    }

    @Override
    @Transactional
    public void deleteAgreement(Long agreementId) {
        Agreement agreement = agreementRepository.findById(agreementId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));

        // 멤버가 동의한 약관이 있다면 삭제 불가능
        if (!memberAgreementListRepository.findByAgreement(agreement).isEmpty()) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_DELETE_AGREEMENT);
        }

        agreementRepository.deleteById(agreementId);
    }

    @Override
    public List<AgreementResponseDto> getAgreementByMemberUuid(String memberUuid) {
        return memberAgreementListRepository.findMemberAgreementListByMemberUuidAndIsAgreeIsTrue(memberUuid)
                .stream()
                .map(memberAgreementList ->
                        AgreementResponseDto.from(memberAgreementList.getAgreement()))
                .toList();
    }

    @Override
    public AgreementResponseDto getAgreementByMemberUuidAndAgreementId(String memberUuid, Long agreementId) {
        return memberAgreementListRepository.findMemberAgreementListByMemberUuidAndIsAgreeIsTrue(memberUuid)
                .stream()
                .filter(memberAgreementList
                        -> memberAgreementList.getAgreement().getId().equals(agreementId))
                .findFirst()
                .map(memberAgreementList
                        -> AgreementResponseDto.from(memberAgreementList.getAgreement()))
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));
    }

    @Override
    @Transactional
    public void createMemberAgreementList(MemberAgreementListCreateRequestDto memberAgreementListRequestDto) {
        memberAgreementListRepository.save(memberAgreementListRequestDto.toEntity());
    }

    @Override
    @Transactional
    public void updateMemberAgreementList(MemberAgreementListUpdateRequestDto memberAgreementListUpdateRequestDto) {
        MemberAgreementList savedMemberAgreementList =
                memberAgreementListRepository.findById(memberAgreementListUpdateRequestDto.getMemberAgreementId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_AGREEMENT));

        // 기존에 저장된 데이터를 toEntity에 넣음
        // 삼항연산자로 인자로 들어온 dto의 값만 업데이트
        // dto에 빈 값으로 들어오면 db에 저장된 데이터로 셋팅
        memberAgreementListRepository.save(memberAgreementListUpdateRequestDto.toEntity(savedMemberAgreementList));

    }
}
