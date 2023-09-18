package com.aw.arbanware.domain.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter @Setter
public class ProductImage implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Id @GeneratedValue
    private int sequence;

    private String storedPath;
    private String storedFileName;
    private String originalFileName;
    private Long fileSize;
}
