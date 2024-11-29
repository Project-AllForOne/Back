package com.banghyang.recommend.repository;

import com.banghyang.member.entity.Member;
import com.banghyang.recommend.entity.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findByMemberOrderByTimestampAsc(Member member);
    // 또는 memberId로 검색하고 싶다면
//    List<Chat> findByMember_IdOrderByTimestampAsc(String memberId);
}
