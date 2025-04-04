package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartCustomRepository {

    /**
     * 사용자가 장바구니에 새로운 상품 종류를 추가할 수 있는지 여부를 반환합니다.
     * (총 종류 수 < 20개일 때 true)
     */
    boolean canAddMoreProductKindToCart(String memberUuid);

    /**
     * 특정 사용자의 장바구니에서 productCode 기준 총 수량을 조회합니다.
     */
    int sumQuantityByProductCode(String memberUuid, String productCode);

    /**
     * 사용자의 장바구니에 특정 상품 + 옵션 조합이 존재하는지 조회합니다.
     */
    Optional<Cart> findCartByProductOptionAndEngraving(String memberUuid, String productCode, Long optionId, String engravingMessage);

    /**
     * 사용자의 장바구니에 특정 상품이 존재하는지 조회합니다.
     */
    public boolean existsByMemberUuidAndProductCode(String memberUuid, String productCode);

}
