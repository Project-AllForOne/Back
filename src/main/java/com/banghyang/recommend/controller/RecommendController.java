package com.banghyang.recommend.controller;

import com.banghyang.recommend.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@RestController
@RequestMapping("/")
public class RecommendController {

    @Autowired
    private RecommendService recommendService;

    @PostMapping("/recommend")
    public ResponseEntity<Map<String, Object>> recommendPerfume(
            @RequestParam("user_input") String userInput,
//            @RequestParam("member_id") long memberId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        // 사용자 입력과 이미지 처리
        Map<String, Object> response = recommendService.processInputAndImage(userInput, image);
        return ResponseEntity.ok(response);
    }
}
