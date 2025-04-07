package com.example.shinsekai.category.infrastructure;

import com.example.shinsekai.category.entity.MainCategory;
import com.example.shinsekai.category.entity.QProductCategoryList;
import com.example.shinsekai.option.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductCategoryListCustomRepoImpl implements ProductCategoryListCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public List<Size> findSizesByMainCategory(Long mainCategoryId) {
        QProductCategoryList productCategoryList = QProductCategoryList.productCategoryList;
        QProductOptionList productOptionList = QProductOptionList.productOptionList;
        QSize size = QSize.size;

        return jpaQueryFactory
                .selectDistinct(size)
                .from(productCategoryList)
                .join(productOptionList).on(productCategoryList.productCode.eq(productOptionList.productCode))
                .join(size).on(productOptionList.sizeId.eq(size.id))
                .where(productCategoryList.mainCategoryId.eq(mainCategoryId))
                .fetch();
    }

    public List<Color> findColorsByMainCategory(Long mainCategoryId) {
        QProductCategoryList productCategoryList = QProductCategoryList.productCategoryList;
        QProductOptionList productOptionList = QProductOptionList.productOptionList;
        QColor color = QColor.color;

        return jpaQueryFactory
                .selectDistinct(color)
                .from(productCategoryList)
                .join(productOptionList).on(productCategoryList.productCode.eq(productOptionList.productCode))
                .join(color).on(productOptionList.colorId.eq(color.id))
                .where(productCategoryList.mainCategoryId.eq(mainCategoryId))
                .fetch();
    }
}
