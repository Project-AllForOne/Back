package com.banghyang.object.review.controller;

import com.banghyang.object.review.dto.MyReviewResponse;
import com.banghyang.object.review.dto.ReviewRequest;
import com.banghyang.object.review.dto.ReviewResponse;
import com.banghyang.object.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reviews")
@RestController
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 특정 향수의 리뷰 목록 조회
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProductId(productId));
    }

    /**
     * 특정 회원이 작성한 리뷰 목록 조회
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MyReviewResponse>> getReviewsByMemberId(@PathVariable Long memberId) {
        return ResponseEntity.ok(reviewService.getReviewsByMemberId(memberId));
    }

    /**
     * 모든 리뷰 조회
     */
    @GetMapping("/all")
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    /**
     * 리뷰 생성 메소드
     */
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest) {
        reviewService.createReview(reviewRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 수정 메소드
     */
    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody ReviewRequest reviewRequest) {
//        reviewService.modifyReview(reviewRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 리뷰 삭제 메소드
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리자 리뷰 삭제 메소드
     */

    @DeleteMapping("/{reviewId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteReviewByAdmin(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().build();
    }


}
