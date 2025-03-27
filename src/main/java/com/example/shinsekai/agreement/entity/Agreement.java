package com.example.shinsekai.agreement.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agreement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(unique = true, nullable = false, columnDefinition="CLOB")
    private String agreementContent;

    @Column(length = 8)
    private LocalDate storageExpiredDate;

}
