package com.example.shinsekai.card.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MemberStarbucksCardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberStarbucksCardUuid;
    private String memberUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    private StarbucksCard starbucksCard;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean active = true;

    @Builder
    public MemberStarbucksCardList(
            String memberStarbucksCardUuid,
            String memberUuid,
            StarbucksCard starbucksCard
    ){
        this.memberStarbucksCardUuid = memberStarbucksCardUuid;
        this.memberUuid = memberUuid;
        this.starbucksCard = starbucksCard;
    }

    public void changeActive(){
        this.active = false;
    }
}
