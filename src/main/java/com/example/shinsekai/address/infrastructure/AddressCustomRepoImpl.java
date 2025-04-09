package com.example.shinsekai.address.infrastructure;

import com.example.shinsekai.address.dto.in.AddressRequestDto;
import com.example.shinsekai.address.entity.QAddress;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@RequiredArgsConstructor
@Repository
public class AddressCustomRepoImpl implements AddressCustomRepository{

    @PersistenceContext
    private EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public boolean canAddAddress(String memberUuid, String addressUuid) {
        QAddress address = QAddress.address;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(address.memberUuid.eq(memberUuid));
        builder.and(address.addressUuid.eq(addressUuid));
        builder.and(address.isDeleted.isFalse());

        Long count = jpaQueryFactory
                .select(address.id)
                .from(address)
                .where(builder)
                .fetchOne();

        return count == null || count == 0;
    }

    public boolean isFirstAddress(String memberUuid) {
        QAddress address = QAddress.address;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(address.memberUuid.eq(memberUuid));
        builder.and(address.isDeleted.isFalse());

        Long count = jpaQueryFactory
                .select(address.count())
                .from(address)
                .where(builder)
                .fetchOne();

        return count == null || count == 0;
    }

    public void dynamicUpdateAddress(AddressRequestDto addressRequestDto) {
        QAddress address = QAddress.address;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(address.memberUuid.eq(addressRequestDto.getMemberUuid()));
        builder.and(address.addressUuid.eq(addressRequestDto.getAddressUuid()));
        builder.and(address.isDeleted.isFalse());

        Long count = jpaQueryFactory.select(address.id).from(address)
                    .where(builder)
                    .fetchOne();
        log.info("count {} ", count);

        JPAUpdateClause update = new JPAUpdateClause(entityManager, address);

        boolean hasUpdate = false;

        // 우편번호
        if (addressRequestDto.getZipNo() != null) {
            update.set(address.zipNo, addressRequestDto.getZipNo());
            hasUpdate = true;
        }
        // 배송지 별칭
        if (addressRequestDto.getAddressNickname() != null) {
            update.set(address.addressNickname, addressRequestDto.getAddressNickname());
            hasUpdate = true;
        }
        // 배송 메모
        if (addressRequestDto.getDeliveryMemo() != null) {
            update.set(address.deliveryMemo, addressRequestDto.getDeliveryMemo());
            hasUpdate = true;
        }
        // 전체 주소
        if (addressRequestDto.getTotalAddress() != null) {
            update.set(address.totalAddress, addressRequestDto.getTotalAddress());
            hasUpdate = true;
        }
        // 메인 주소 여부
        if (addressRequestDto.isMainAddress()) {
            builder.and(address.isMainAddress.isFalse());
            update.set(address.isMainAddress, true);
            hasUpdate = true;
        }else {
            builder.and(address.isMainAddress.isTrue());
            update.set(address.isMainAddress, false);
            hasUpdate = true;
        }

        // 메인 휴대전화 번호
        if (addressRequestDto.getFirstPhoneNumber() != null) {
            update.set(address.firstPhoneNumber, addressRequestDto.getFirstPhoneNumber());
            hasUpdate = true;
        }
        // 서브 휴대전화 번호
        if (addressRequestDto.getSecondPhoneNumber() != null) {
            update.set(address.secondPhoneNumber, addressRequestDto.getSecondPhoneNumber());
            hasUpdate = true;
        }
        // 수령인 이름
        if (addressRequestDto.getReceiverName() != null) {
            update.set(address.receiverName, addressRequestDto.getReceiverName());
            hasUpdate = true;
        }

        if (hasUpdate) {
            long updatedCount = update.where(builder).execute();
            log.info("업데이트된 row 수: {}", updatedCount);
        }

    }
}
