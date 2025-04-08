package com.example.shinsekai.cart.infrastructure;

import com.example.shinsekai.cart.entity.Cart;
import com.example.shinsekai.cart.entity.QCart;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CartCustomRepoImpl implements CartCustomRepository {

    private static final int MAX_CART_PRODUCT_KINDS_PER_USER = 20;

    private final JPAQueryFactory jpaQueryFactory;

    public List<Cart> findByMemberUuid(String memberUuid) {
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(cart.memberUuid.eq(memberUuid));
        builder.and(cart.isDeleted.isFalse());

        List<Cart> cartList = jpaQueryFactory
                .select(cart)
                .from(cart)
                .where(builder)
                .fetch();

        return cartList;
    }

    public boolean canAddMoreProductKindToCart(String memberUuid) {
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(cart.memberUuid.eq(memberUuid));
        builder.and(cart.isDeleted.isFalse());

        Long count = jpaQueryFactory
                .select(cart.productCode.countDistinct())
                .from(cart)
                .where(builder)
                .fetchOne();

        return count == null || count < MAX_CART_PRODUCT_KINDS_PER_USER;
    }

    public int sumQuantityByProductCode(String memberUuid, String productCode) {
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(cart.memberUuid.eq(memberUuid));
        builder.and(cart.productCode.eq(productCode));
        builder.and(cart.isDeleted.isFalse());

        Integer total = jpaQueryFactory
                .select(cart.quantity.sum())
                .from(cart)
                .where(builder)
                .fetchOne();

        return total != null ? total : 0;
    }

    public Optional<Cart> findCartByProductOptionAndEngraving(String memberUuid, String productCode, Long optionId,
                                                              String engravingMessage) {
        QCart cart = QCart.cart;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(cart.memberUuid.eq(memberUuid));
        builder.and(cart.productCode.eq(productCode));
        builder.and(cart.productOptionListId.eq(optionId));
        builder.and(cart.isDeleted.isFalse());
        Optional.ofNullable(engravingMessage).ifPresent(
                message -> builder.and(cart.engravingMessage.eq(message))
        );

        Cart result = jpaQueryFactory
                .selectFrom(cart)
                .where(builder)
                .fetchOne();

        return Optional.ofNullable(result);
    }

    public boolean existsByMemberUuidAndProductCode(String memberUuid, String productCode) {
        QCart cart = QCart.cart;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(cart.memberUuid.eq(memberUuid));
        builder.and(cart.productCode.eq(productCode));
        builder.and(cart.isDeleted.isFalse());

        Integer result = jpaQueryFactory
                .selectOne()
                .from(cart)
                .where(builder)
                .fetchFirst();

        return result != null;
    }
}
