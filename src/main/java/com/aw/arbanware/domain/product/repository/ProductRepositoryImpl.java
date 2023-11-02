package com.aw.arbanware.domain.product.repository;

import com.aw.arbanware.domain.product.Color;
import com.aw.arbanware.domain.product.Size;
import com.aw.arbanware.domain.product.controller.ProductSearchCondition;
import com.aw.arbanware.domain.product.entity.Product;
import com.aw.arbanware.domain.product.entity.QProduct;
import com.aw.arbanware.domain.product.entity.QProductInfo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

import static com.aw.arbanware.domain.product.entity.QProduct.product;
import static com.aw.arbanware.domain.product.entity.QProductInfo.productInfo;
import static org.springframework.util.StringUtils.*;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<ProductProductInfoDto> search(ProductSearchCondition condition, Pageable pageable){
        final QueryResults<ProductProductInfoDto> result = queryFactory
                .select(new QProductProductInfoDto(
                        product.id.as("productId"),
                        product.name,
                        product.price,
                        product.thumbnail
                ))
                .from(product)
                .where(
                        nameEq(condition.getName()),
                        priceBetween(condition.getMinPrice(), condition.getMaxPrice())

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }

    private BooleanExpression colorEq(List<Color> colors) {
        return colors != null && !colors.isEmpty() ? productInfo.color.in(colors) : null;
    }

    private BooleanExpression sizeEq(List<Size> sizes) {
        return sizes != null && !sizes.isEmpty() ? productInfo.size.in(sizes) : null;
    }

    private BooleanBuilder priceBetween(String  minPrice, String  maxPrice) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(minPrice(minPrice));
        builder.and(maxPrice(maxPrice));
        return builder;
    }

    private BooleanExpression minPrice(String  minPrice) {
        return hasText(minPrice) ? product.price.loe(Integer.parseInt(minPrice)) : null;
    }

    private BooleanExpression maxPrice(String  maxPrice) {
        return hasText(maxPrice) ? product.price.goe(Integer.parseInt(maxPrice)) : null;
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? product.name.eq(name) : null;
    }
}