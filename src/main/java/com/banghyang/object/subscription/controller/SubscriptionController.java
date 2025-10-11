package com.banghyang.object.subscription.controller;

import com.banghyang.object.subscription.dto.SubscriptionCreateRequest;
import com.banghyang.object.subscription.dto.SubscriptionResponse;
import com.banghyang.object.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/subscription")
@RestController
@RequiredArgsConstructor
@Slf4j

public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * 구독 생성
     * 회원이 특정 상품에 대해 구독을 생성하는 API
     *
     * @param request 구독 생성 요청 DTO (memberId, productId 포함)
     * @return 생성된 구독 정보 또는 에러 메시지
     */
    @PostMapping
    public ResponseEntity<?> createSubscription(@RequestBody SubscriptionCreateRequest request) {

        Long memberId = request.getMemberId();
        Long productId = request.getProductId();

        log.info("👍 [구독 요청] memberId={}, productId={}", memberId, productId);

        try {
            SubscriptionResponse response = subscriptionService.subscribe(memberId, productId);
            log.info("✅ [구독 성공] memberId={}, productId={}, subscriptionId={}",
                    memberId, productId, response.getSubscriptionId());
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            log.error("❌ [구독 실패 - 중복 구독] {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("❌ [구독 실패] {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * 구독 취소
     * 활성 상태인 구독을 취소 처리하는 API
     *
     * @param subscriptionId 취소할 구독의 ID
     * @param memberId 구독 취소를 요청한 회원의 ID
     * @return 구독 취소 성공 메시지 또는 에러 메시지
     */
    @DeleteMapping("/{subscriptionId}/{memberId}")
    public ResponseEntity<?> cancelSubscription(@PathVariable("subscriptionId") Long subscriptionId,
                                                @PathVariable("memberId") Long memberId) {
        log.info("🗑️ [구독 취소 요청] subscriptionId={}, memberId={}", subscriptionId, memberId);

        if (subscriptionId == null || memberId == null) {
            log.error("❌ subscriptionId 또는 memberId가 null입니다! 요청을 확인하세요.");
            return ResponseEntity.badRequest().body("subscriptionId 또는 memberId가 null입니다.");
        }

        try {
            subscriptionService.cancelSubscription(subscriptionId, memberId);
            log.info("✅ [구독 취소 완료] subscriptionId={}, memberId={}", subscriptionId, memberId);
            return ResponseEntity.ok().body("구독이 정상적으로 취소되었습니다.");
        } catch (IllegalArgumentException e) {
            log.error("❌ [구독 취소 실패] {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * 내 구독 목록 조회
     * 회원의 전체 구독 이력(활성/취소 모두)을 조회하는 API
     * 구독 관리 페이지에서 사용되며, 취소된 구독도 함께 반환하여 재구독 가능하게 함
     *
     * @param memberId 구독 목록을 조회할 회원의 ID
     * @return 회원의 전체 구독 이력 리스트 또는 에러 메시지
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getMySubscriptions(@PathVariable("memberId") Long memberId) {

        log.info("🔍 [전체 구독 이력 조회 요청] memberId={}", memberId);

        if (memberId == null) {
            log.error("❌ memberId가 null입니다! 요청을 확인하세요.");
            return ResponseEntity.badRequest().body("memberId가 null입니다.");
        }

        try {
            List<SubscriptionResponse> subscriptions = subscriptionService.getMyAllSubscriptions(memberId);
            log.info("✅ [전체 구독 이력 조회 성공] memberId={} 총 구독 개수: {}", memberId, subscriptions.size());
            return ResponseEntity.ok(subscriptions);
        } catch (IllegalArgumentException e) {
            log.error("❌ [전체 구독 이력 조회 실패] {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}