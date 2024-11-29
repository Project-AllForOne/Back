package com.banghyang.recommend.service;

import com.banghyang.member.entity.Member;
import com.banghyang.member.repository.MemberRepository;
import com.banghyang.recommend.entity.Chat;
import com.banghyang.recommend.repository.ChatRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecommendService {

    private final LLMService llmService;
    private final ImageProcessingService imageProcessingService;
    private final ImageGenerationService imageGenerationService;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    // 생성자 주입
    public RecommendService(LLMService llmService, ImageProcessingService imageProcessingService, ImageGenerationService imageGenerationService, ChatRepository chatRepository, MemberRepository memberRepository) {
        this.llmService = llmService;
        this.imageProcessingService = imageProcessingService;
        this.imageGenerationService = imageGenerationService;
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
    }

    // 사용자 입력과 이미지를 처리하는 메서드
    public Map<String, Object> processInputAndImage(String userInput,  MultipartFile image) {
        Map<String, Object> response = new HashMap<>();
        try {
            // memberId로 Member 객체 조회
//            Member member = memberRepository.findById(memberId)
//                    .orElseThrow(() -> new RuntimeException("Member not found"));

            // 사용자 입력 저장
            Chat userChat = Chat.builder()
//                    .member(member)        // Member 객체 설정
                    .content(userInput)
                    .type(Chat.MessageType.USER)
                    .timestamp(LocalDateTime.now())
                    .build();
            chatRepository.save(userChat);

            // 1. 사용자 입력 처리 (LLMService 사용)
            Map<String, Object> llmResponse = llmService.processInputFromFastAPI(userInput);
            String mode = (String) llmResponse.get("mode");

            if ("recommendation".equals(mode)) {
                // 향수 추천 및 공통 감정 처리
                response.put("recommendedPerfumes", llmResponse.get("recommended_perfumes"));
                response.put("commonFeeling", llmResponse.get("common_feeling"));

                // 2. 이미지 처리 (이미지 파일이 있으면 처리)
                if (image != null) {
                    Map<String, Object> imageProcessingResult = imageProcessingService.processImage(image);
                    response.put("imageProcessed", imageProcessingResult);
                }

                // 3. 공통 감정에 따라 이미지 생성
                String prompt = "공통 감정: " + llmResponse.get("common_feeling");
                Map<String, Object> generatedImageResult = imageGenerationService.generateImage(prompt);
                response.put("generatedImage", generatedImageResult.get("output_path"));

                // AI 응답 저장할 때도 동일하게 Member 객체 사용
                Chat aiChat = Chat.builder()
//                        .member(member)
                        .content(llmResponse.toString())
                        .type(Chat.MessageType.AI)
                        .timestamp(LocalDateTime.now())
                        .build();
                chatRepository.save(aiChat);

            } else {
                // 대화 모드 처리
                String chatResponse = (String) llmResponse.get("response");
                response.put("mode", "chat");
                response.put("response", chatResponse);

                // AI 응답 저장 (채팅 모드)
                Chat aiChat = Chat.builder()
//                        .member(member)
                        .content(chatResponse)
                        .type(Chat.MessageType.AI)
                        .timestamp(LocalDateTime.now())
                        .build();
                chatRepository.save(aiChat);
            }

        }catch (Exception e) {
            e.printStackTrace();  // 콘솔에 에러 로그 출력
            response.put("error", "처리 중 오류가 발생했습니다: " + e.getMessage());  // 구체적인 에러 메시지 포함
            response.put("errorType", e.getClass().getSimpleName());  // 에러 타입 포함
        }
        return response;
    }
}
