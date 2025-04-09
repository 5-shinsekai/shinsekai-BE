package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartCustomRepository {
    boolean canAddMoreProductKindToCart(String memberUuid);
    List<Cart> findByMemberUuid(String memberUuid);
    List<Cart> findAllCartByProduct(String memberUuid, String productCode);
}
