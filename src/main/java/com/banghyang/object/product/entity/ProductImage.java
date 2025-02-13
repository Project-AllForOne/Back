package com.banghyang.object.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 제품 이미지 아이디
    private String url; // 제품 이미지 url
    private String noBgUrl; // 배경 제거 후 이미지 url

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 제품 아이디

    // 빌더
    @Builder
    public ProductImage(Product product, String url, String noBgUrl) {
        this.product = product;
        this.url = url;
        this.noBgUrl = noBgUrl;
    }
}
