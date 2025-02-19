package com.banghyang.object.review.dto;

public class ReviewResponse {
    private Long id; // 리뷰 아이디
    private String name; // 작성자명
    private String content; // 리뷰 내용
    private boolean heart; // 좋아요 유무
    private int heartCount; // 좋아요 개수

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReviewResponse {
    private Long id;
    private String name;  // 리뷰 작성자
    private String content; // 리뷰 내용
    private LocalDateTime createdAt; // 작성 날짜
}
