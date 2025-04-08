package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartCustomRepository {
    boolean canAddMoreProductKindToCart(String memberUuid);
    int sumQuantityByProductCode(String memberUuid, String productCode);
    Optional<Cart> findCartByProductOptionAndEngraving(String memberUuid, String productCode, Long optionId, String engravingMessage);
    public boolean existsByMemberUuidAndProductCode(String memberUuid, String productCode);
    List<Cart> findByMemberUuid(String memberUuid);
}
