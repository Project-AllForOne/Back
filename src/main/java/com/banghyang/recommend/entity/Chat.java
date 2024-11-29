package com.banghyang.recommend.entity;

import com.banghyang.member.entity.Member;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Builder
@Document(collection = "chat")
public class Chat {

    @Id
    private String id;

    @Field("member_id")
    @DocumentReference(lazy = true)  // Member 컬렉션을 참조
    private Member member;

    @Field("content")
    private String content;

    @Field("type")
    private MessageType type;  // enum으로 변경

    @Field("timestamp")
    private LocalDateTime timestamp;


//    private String imageUrl;

    // MessageType enum 정의
    public enum MessageType {
        USER,
        AI
    }
}