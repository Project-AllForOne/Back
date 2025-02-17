package com.banghyang.object.product.dto;

import com.banghyang.object.review.dto.ReviewResponse;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailResponse {
    private List<SimilarProductResponse> noteSimilarProductList;
    private List<SimilarProductResponse> imageSimilarProductList;
    private List<ReviewResponse> reviewList;
}
