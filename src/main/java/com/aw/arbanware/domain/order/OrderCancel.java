package com.aw.arbanware.domain.order;

import com.aw.arbanware.domain.orderproduct.OrderProduct;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class OrderCancel {

    @Id @GeneratedValue
    @Column(name = "ORDER_CANCEL_ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REFUND_ID")
    private Refund refund; //환불 번호

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_PRODUCT_ID")
    private OrderProduct orderProduct; //주문 상품 번호

    private String status; //입금 상태
    private String progress; //진행 상태
    private LocalDateTime applyDate; //신청일
    private int amount; //취소금액

}
