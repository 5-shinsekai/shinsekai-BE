package com.example.shinsekai.notification.infrastructure;

import com.example.shinsekai.notification.entity.RestockNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RestockNotificationRepository extends JpaRepository<RestockNotification, Long> {

    boolean existsByMemberUuidAndProductOptionIdAndNotifiedFalse(String memberUuid, Long productOptionId);

    List<RestockNotification> findAllByProductOptionIdAndNotifiedFalseAndValidUntilAfter(
            Long productOptionId,
            LocalDateTime now
    );
}
