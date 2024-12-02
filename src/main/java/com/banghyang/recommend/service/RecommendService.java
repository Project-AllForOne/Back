package com.banghyang.recommend.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecommendService {

    private final LLMService llmService;
    private final ImageGenerationService imageGenerationService;

    // 생성자 주입
    public RecommendService(LLMService llmService, ImageGenerationService imageGenerationService) {
        this.llmService = llmService;
        this.imageGenerationService = imageGenerationService;
    }

    // 사용자 입력과 이미지를 처리하는 메서드
    public Map<String, Object> processInputAndImage(String userInput) {
        Map<String, Object> response = new HashMap<>();
        try {
            // LLM 서비스 호출하여 결과 받기
            Map<String, Object> llmResponse = llmService.processInputFromFastAPI(userInput);

            String mode = (String) llmResponse.get("mode");

            if ("recommendation".equals(mode)) {
                // 향수 추천 및 공통 감정 처리
                response.put("mode", "recommendation");
                response.put("recommendedPerfumes", llmResponse.get("recommended_perfumes"));
                response.put("commonFeeling", llmResponse.get("common_feeling"));

                // 공통 감정과 이미지 프롬프트 결합하여 이미지 생성 요청
                String commonFeeling = (String) llmResponse.get("common_feeling");
                String imagePrompt = (String) llmResponse.get("image_prompt"); // 이미지 프롬프트 받아오기
                String prompt = "Generate an image based on the following feeling: " + imagePrompt;

                // 공통 감정을 프롬프트에 포함시켜 이미지 생성 요청
                Map<String, Object> generatedImageResult = imageGenerationService.generateImage(prompt);
                response.put("generatedImage", generatedImageResult.get("output_path"));

                // 이미지 생성만 수행하고 결과 반환
                Map<String, Object> additionalGeneratedImageResult = imageGenerationService.generateImage(prompt);
                response.put("generatedImage", additionalGeneratedImageResult);
            } else if ("chat".equals(mode)) {
                // 대화 모드 처리
                String chatResponse = (String) llmResponse.get("response");
                response.put("mode", "chat");
                response.put("response", chatResponse);
            } else {
                throw new IllegalArgumentException("LLM 응답에서 알 수 없는 모드: " + mode);
            }
        } catch (IllegalArgumentException e) {
            // 알 수 없는 모드 예외 처리
            System.err.println("LLM 서비스 오류: " + e.getMessage());
            response.put("error", "Invalid mode from LLM response: " + e.getMessage());
        } catch (NullPointerException e) {
            // 예상치 못한 null 값 처리
            System.err.println("LLM 응답에 예상되지 않은 null 값이 포함됨: " + e.getMessage());
            response.put("error", "Unexpected null value in LLM response: " + e.getMessage());
        } catch (Exception e) {
            // 일반적인 예외 처리
            System.err.println("추천 처리 중 예외 발생: " + e.getMessage());
            response.put("error", "Processing error: " + e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}