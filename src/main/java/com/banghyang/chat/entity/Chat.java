package com.banghyang.chat.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private String memberId;            // memberID(어떤건지 물어보고 바뀔수도있) 이것만으로도 대화 구분 가능
    private String content;             // 채팅내용
    private String type;                // "USER" 또는 "AI"
    private LocalDateTime timestamp;    // 채팅친 시간
}
