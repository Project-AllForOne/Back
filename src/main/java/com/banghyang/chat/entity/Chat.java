package com.banghyang.chat.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
public class Chat {

    @Id
    private String id;
    private String sessionId;   // 이것만으로도 대화 구분 가능
    private String content;
    private String type;        // "USER" 또는 "AI"
    private LocalDateTime timestamp;
}
