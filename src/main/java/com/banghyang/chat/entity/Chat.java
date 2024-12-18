package com.banghyang.chat.entity;

import com.banghyang.common.type.ChatMode;
import com.banghyang.common.type.ChatType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "chat")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    @Id
    private String id; // MongoDB 에서 자동 생성되는 ID

    @Field("type")
    private ChatType type; // 메시지 타입 (USER, AI)

    @Field("member_id")
    private Long memberId; // 회원 ID

    @Field("content")
    private String content; // 회원 채팅 텍스트 입력값

    @Field("image_url")
    private String imageUrl; // 회원 채팅 이미지 입력값

    @Field("mode")
    private ChatMode mode; // ai 채팅 답변 모드(chat, recommend)

    @Field("line_id")
    private Long lineId; // 향수 추천 기준 계열 아이디

    @Field("recommendations")
    private List<Recommendation> recommendations; // 향수 추천 내용

    @Field("timeStamp")
    @CreatedDate
    private LocalDateTime timeStamp; // 메시지 생성 시간

    @Data
    public static class Recommendation {
        @Field("perfume_name")
        private String perfumeName;
        @Field("perfume_image_url")
        private String perfumeImageUrl;
        @Field("perfume_brand")
        private String perfumeBrand;
        @Field("perfume_grade")
        private String perfumeGrade;
        @Field("reason")
        private String reason;
        @Field("situation")
        private String situation;
    }

    @Builder
    public Chat(
            ChatType type,
            Long memberId,
            String content,
            String imageUrl,
            ChatMode mode,
            Long lineId,
            List<Recommendation> recommendations
    ) {
        this.type = type;
        this.memberId = memberId;
        this.content = content;
        this.imageUrl = imageUrl;
        this.mode = mode;
        this.lineId = lineId;
        this.recommendations = recommendations;
    }
}
