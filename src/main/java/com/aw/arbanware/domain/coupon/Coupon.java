package com.aw.arbanware.domain.coupon;

import com.aw.arbanware.domain.common.baseentity.BaseEntity;
import com.aw.arbanware.domain.common.DeleteYn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="CTYPE")
public class Coupon extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "coupon_seq")
    @SequenceGenerator(name = "coupon_seq",sequenceName = "COUPON_SEQUENCE",allocationSize = 1)
    @Column(name = "COUPON_ID")
    private Long id; //쿠폰번호

    private String name; //쿠폰명
    private int period; //사용기간

    @Enumerated(EnumType.STRING)
    private DeleteYn deleteYn; //삭제여부

}
