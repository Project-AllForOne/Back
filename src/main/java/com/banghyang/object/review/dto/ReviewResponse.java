package com.banghyang.object.review.dto;

public class ReviewResponse {
    private Long id; // 리뷰 아이디
    private String name; // 작성자명
    private String content; // 리뷰 내용
    private boolean heart; // 좋아요 유무
    private int heartCount; // 좋아요 개수
}
