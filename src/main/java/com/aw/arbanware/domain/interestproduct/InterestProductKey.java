package com.aw.arbanware.domain.interestproduct;

import com.aw.arbanware.domain.product.Product;
import com.aw.arbanware.domain.user.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class InterestProductKey implements Serializable {

    private Product product;
    private Member member;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InterestProductKey that = (InterestProductKey) o;
        return Objects.equals(product, that.product) && Objects.equals(member, that.member);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, member);
    }
}