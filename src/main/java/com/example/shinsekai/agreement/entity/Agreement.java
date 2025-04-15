package com.example.shinsekai.agreement.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agreement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String agreementTitle;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String agreementContent;

    private LocalDate storedExpiredDate;

    @Builder
    public Agreement(Long id,
                     String agreementTitle,
                     String agreementContent,
                     LocalDate storedExpiredDate) {
        this.id = id;
        this.agreementTitle = agreementTitle;
        this.agreementContent = agreementContent;
        this.storedExpiredDate = storedExpiredDate;
    }
}
