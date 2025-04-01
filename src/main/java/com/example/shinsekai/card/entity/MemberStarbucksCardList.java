package com.example.shinsekai.card.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStarbucksCardList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String memberStarbucksCardUuid;
    private String memberUuid;

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private StarbucksCard starbucksCard;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active = true;

    @Builder
    public MemberStarbucksCardList(long id, String memberStarbucksCardUuid, String memberUuid, StarbucksCard starbucksCard, boolean active){
        this.id = id;
        this.memberStarbucksCardUuid = memberStarbucksCardUuid;
        this.memberUuid = memberUuid;
        this.starbucksCard = starbucksCard;
        this.active = active;
    }

    public void changeActive(){
        this.active = false;
    }
}
