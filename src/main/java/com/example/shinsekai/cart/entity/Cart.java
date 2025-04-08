package com.example.shinsekai.cart.entity;

import com.example.shinsekai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Entity
@Builder
@NoArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String cartUuid;

    @Column(nullable = false, length = 50)
    private String memberUuid;

    @Column(nullable = false)
    private Long productOptionListId;

    @Column(nullable = false, length = 50)
    private String productCode;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    @Builder.Default
    private boolean checked = true;

    @Column(length = 10)
    private String engravingMessage;

    @Column(nullable = false)
    private boolean isFrozen;

    @Column(nullable = false)
    @Builder.Default
    private boolean isDeleted = false;

    @Builder
    public Cart(Long id, String cartUuid, String memberUuid, Long productOptionListId, String productCode, int quantity, boolean checked,
                String engravingMessage, boolean isFrozen, boolean isDeleted) {
        this.id = id;
        this.cartUuid = cartUuid;
        this.memberUuid = memberUuid;
        this.productOptionListId = productOptionListId;
        this.productCode = productCode;
        this.quantity = quantity;
        this.checked = checked;
        this.engravingMessage = engravingMessage;
        this.isFrozen = isFrozen;
        this.isDeleted = isDeleted;
    }

    public void increaseQuantity(int i) {
        this.quantity += i;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setDeleted(){
        this.isDeleted = false;
    }
}