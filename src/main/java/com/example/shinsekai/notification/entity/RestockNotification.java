package com.example.shinsekai.notification.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "restock_notification",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"memberUuid", "productOptionId"})
        }
)
public class RestockNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberUuid;

    @Column(nullable = false, unique = true)
    private Long productOptionId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(nullable = false)
    private boolean notified = false;

    @Column
    private LocalDateTime notifiedAt;

    @Builder
    public RestockNotification(
            String memberUuid,
            Long productOptionId,
            LocalDateTime requestedAt,
            LocalDateTime validUntil) {
        this.memberUuid = memberUuid;
        this.productOptionId = productOptionId;
        this.requestedAt = requestedAt;
        this.validUntil = validUntil;
    }

    public void markAsNotified() {
        this.notified = true;
        this.notifiedAt = LocalDateTime.now();
    }
}
