package com.example.shinsekai.notification.infrastructure;

import com.example.shinsekai.notification.entity.RestockNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestockNotificationRepository extends JpaRepository<RestockNotification, Long> {

    List<RestockNotification> findByMemberUuid(String memberUuid);

    boolean existsByMemberUuidAndProductOptionIdAndMailNotifiedFalseAndSseNotifiedFalse(String memberUuid, Long productOptionId);

    @Query("SELECT r FROM RestockNotification r " +
            "WHERE r.productOptionId = :productOptionId " +
            "AND r.memberUuid = :memberUuid " +
            "AND r.mailNotified = false " +
            "AND r.sseNotified = false " +
            "AND r.validUntil > CURRENT_TIMESTAMP")
    List<RestockNotification> findValidUnnotifiedByMemberUuidAndProductOptionId(
            @Param("productOptionId") Long productOptionId, @Param("memberUuid") String memberUuid);


    @Query("SELECT r FROM RestockNotification r " +
            "WHERE r.productOptionId = :productOptionId " +
            "AND r.memberUuid = :memberUuid " +
            "AND r.mailNotified = true " +
            "AND r.sseNotified = false " +
            "AND r.validUntil > CURRENT_TIMESTAMP")
    List<RestockNotification> findValidUnSseNotifiedByMemberUuidAndProductOptionId(
            @Param("productOptionId") Long productOptionId, @Param("memberUuid") String memberUuid);


    @Query("SELECT r FROM RestockNotification r " +
            "WHERE r.productOptionId = :productOptionId " +
            "AND r.mailNotified = false " +
            "AND r.sseNotified = false " +
            "AND r.validUntil > CURRENT_TIMESTAMP")
    List<RestockNotification> findValidUnnotificationByProductOptionId(@Param("productOptionId") Long productOptionId);

    @Query("SELECT r FROM RestockNotification r " +
            "WHERE r.memberUuid = :memberUuid " +
            "AND r.mailNotified = true " +
            "AND r.sseNotified = false " +
            "AND r.validUntil > CURRENT_TIMESTAMP")
    List<RestockNotification> findUnSseNotificationByProductOptionId(@Param("memberUuid") String memberUuid);

    @Query(value = """
                SELECT c.product_name
                FROM restock_notification a
                JOIN product_option_list b ON a.product_option_id = b.id
                JOIN product c ON b.product_code = c.product_code
                WHERE c.is_deleted = false
                  AND b.option_status = 'IN_STOCK'
                  AND c.product_status = 'SELLING'
                  AND a.sse_notified = false
                  AND a.member_uuid = :memberUuid
            """, nativeQuery = true)
    List<String> findProductNamesToNotifyByMember(@Param("memberUuid") String memberUuid);

    List<RestockNotification> findByMemberUuidAndSseNotifiedIsFalse(String memberUuid);

}
