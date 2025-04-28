package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl implements ProductSearchRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<String> searchByProductName(String keyword, Pageable pageable) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(product.isDeleted.isFalse());
        builder.and(product.productStatus.eq(ProductStatus.SELLING));

        if (keyword != null && !keyword.isBlank()) {
            builder.and(product.productName.containsIgnoreCase(keyword));
        }

        List<String> productCodes = queryFactory
                .select(product.productCode)
                .from(product)
                .where(builder)
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(product.count())
                .from(product)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(productCodes, pageable, total == null ? 0 : total);
    }
}
