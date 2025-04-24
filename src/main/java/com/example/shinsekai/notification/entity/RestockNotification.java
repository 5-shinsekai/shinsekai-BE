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

    @Column(nullable = false)
    private String memberUuid;

    @Column(nullable = false)
    private Long productOptionId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime requestedAt;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(nullable = false)
    private Boolean mailNotified = false;

    @Column(nullable = false)
    private Boolean sseNotified = false;

    @Column
    private LocalDateTime mailNotifiedAt;

    @Column
    private LocalDateTime sseNotifiedAt;

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

    public void markAsMailNotified() {
        this.mailNotified = true;
        this.mailNotifiedAt = LocalDateTime.now();
    }

    public void markAsSseNotified() {
        this.sseNotified = true;
        this.sseNotifiedAt = LocalDateTime.now();
    }
}
