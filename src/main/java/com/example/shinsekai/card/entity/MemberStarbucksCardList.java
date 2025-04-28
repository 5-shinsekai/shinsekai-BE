package com.example.shinsekai.card.entity;


import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberStarbucksCardList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberStarbucksCardUuid;
    private String memberUuid;

    private String StarbucksCardUuid;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

    private LocalDateTime deletedAt;

    @Builder
    public MemberStarbucksCardList(
            String memberStarbucksCardUuid,
            String memberUuid,
            String starbucksCardUuid
    ) {
        this.memberStarbucksCardUuid = memberStarbucksCardUuid;
        this.memberUuid = memberUuid;
        this.StarbucksCardUuid = starbucksCardUuid;
    }

    public void softDelete() {
        this.active = false;
        this.deletedAt = LocalDateTime.now();
    }
}
