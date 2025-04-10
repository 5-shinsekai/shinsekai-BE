package com.example.shinsekai.product.infrastructure;

import com.example.shinsekai.category.entity.QProductCategoryList;
import com.example.shinsekai.option.entity.QProductOptionList;
import com.example.shinsekai.product.entity.ProductStatus;
import com.example.shinsekai.product.entity.QProduct;
import com.example.shinsekai.season.entity.QProductSeasonList;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
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
    public Page<String> searchProductCodes(
            Long mainCategoryId,
            List<Long> subCategoryIds,
            List<Integer> seasonIds,
            List<Long> sizeIds,
            String priceRange,
            Pageable pageable
    ) {
        QProduct product = QProduct.product;
        QProductCategoryList categoryList = QProductCategoryList.productCategoryList;
        QProductSeasonList seasonList = QProductSeasonList.productSeasonList;
        QProductOptionList optionList = QProductOptionList.productOptionList;

        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건: 삭제 X, 판매 중
        builder.and(product.isDeleted.isFalse());
        builder.and(product.productStatus.eq(ProductStatus.SELLING));

        // Main Category 단일 조건
        builder.and(categoryList.mainCategoryId.eq(mainCategoryId));

        // Sub Category 다중 조건
        if (subCategoryIds != null && !subCategoryIds.isEmpty()) {
            builder.and(categoryList.subCategoryId.in(subCategoryIds));
        }

        // 시즌 다중 조건
        if (seasonIds != null && !seasonIds.isEmpty()) {
            builder.and(seasonList.seasonId.in(seasonIds));
        }

        // 사이즈 다중 조건
        if (sizeIds != null && !sizeIds.isEmpty()) {
            builder.and(optionList.sizeId.in(sizeIds));
        }

        // 가격 조건 (실구매가 = productPrice * (1 - discountRate / 100.0))
        if (priceRange != null && priceRange.contains("-")) {
            String[] range = priceRange.split("-");
            double min = Double.parseDouble(range[0]);
            double max = Double.parseDouble(range[1]);

            NumberTemplate<Double> finalPrice = Expressions.numberTemplate(
                    Double.class,
                    "({0} * (1 - ({1} / 100.0)))",
                    product.productPrice,
                    product.discountRate
            );

            builder.and(finalPrice.between(min, max));
        }

        // 상품 코드 조회
        List<String> results = queryFactory
                .select(product.productCode)
                .from(product)
                .join(categoryList).on(product.productCode.eq(categoryList.productCode))
                .leftJoin(seasonList).on(product.productCode.eq(seasonList.productCode))
                .leftJoin(optionList).on(product.productCode.eq(optionList.productCode))
                .where(builder)
                .groupBy(product.productCode)
                .orderBy(product.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 개수 조회
        Long total = queryFactory
                .select(product.countDistinct())
                .from(product)
                .join(categoryList).on(product.productCode.eq(categoryList.productCode))
                .leftJoin(seasonList).on(product.productCode.eq(seasonList.productCode))
                .leftJoin(optionList).on(product.productCode.eq(optionList.productCode))
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total == null ? 0 : total);
    }
}
