package com.example.shinsekai.notification.application;

import com.example.shinsekai.common.entity.BaseResponseStatus;
import com.example.shinsekai.common.exception.BaseException;
import com.example.shinsekai.email.application.EmailService;
import com.example.shinsekai.member.entity.Member;
import com.example.shinsekai.member.infrastructure.MemberRepository;
import com.example.shinsekai.notification.dto.in.RestockNotificationRequestDto;
import com.example.shinsekai.notification.entity.RestockNotification;
import com.example.shinsekai.notification.infrastructure.RestockNotificationRepository;
import com.example.shinsekai.option.entity.OptionStatus;
import com.example.shinsekai.option.entity.ProductOptionList;
import com.example.shinsekai.option.infrastructure.ProductOptionListRepository;
import com.example.shinsekai.product.entity.Product;
import com.example.shinsekai.product.infrastructure.ProductRepository;
import com.example.shinsekai.sse.application.SseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestockNotificationServiceImpl implements RestockNotificationService {

    private final RestockNotificationRepository restockNotificationRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProductOptionListRepository productOptionListRepository;
    private final SseService sseService;

    private final EmailService emailService;

    @Override
    @Transactional
    public void register(String memberUuid, RestockNotificationRequestDto dto) {
        ProductOptionList productOptionList = productOptionListRepository.findById(dto.getProductOptionId())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTIONS_IN_PRODUCT));
        if (!productOptionList.getOptionStatus().equals(OptionStatus.OUT_OF_STOCK)) {
            throw new BaseException(BaseResponseStatus.INVALID_RESTOCK_NOTIFICATION_CONDITION);
        }

        if (restockNotificationRepository.existsByMemberUuidAndProductOptionIdAndMailNotifiedFalseAndSseNotifiedFalse(
                memberUuid, dto.getProductOptionId())) {
            throw new BaseException(BaseResponseStatus.EXIST_NOTIFICATION_SETTING);
        }

        RestockNotification notification = RestockNotification.builder()
                .memberUuid(memberUuid)
                .productOptionId(dto.getProductOptionId())
                .requestedAt(LocalDateTime.now())
                .validUntil(LocalDateTime.now().plusDays(dto.getDurationDays()))
                .build();

        restockNotificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void notifyForRestockedOption(Long productOptionId) {
        List<RestockNotification> notifications = restockNotificationRepository
                .findAllByProductOptionIdAndMailNotifiedFalseAndSseNotifiedFalseAndValidUntilAfter(productOptionId, LocalDateTime.now());

        for (RestockNotification notice : notifications) {

            Member member = memberRepository.findByMemberUuid(notice.getMemberUuid())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_USER));

            ProductOptionList option = productOptionListRepository.findById(productOptionId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_OPTION));

            Product product = productRepository.findByProductCode(option.getProductCode())
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NO_EXIST_PRODUCT));

            emailService.sendRestockEmail(member.getEmail(), product.getProductName());

            // 메일 보냄 체크
            notice.markAsMailNotified();

            // memberUuid로 서버 접속 여부 체크
            if(sseService.isUserConnected(member.getMemberUuid())) {
                // sse 보냄 체크
                notice.markAsSseNotified();

                // 접속해 있는 회원에게 sse로 재입고 알림을 보냄
                sseService.sendToMember(notice.getMemberUuid(), product.getProductName() + "이(가) 재입고되었습니다!");
            }

        }
    }
}
