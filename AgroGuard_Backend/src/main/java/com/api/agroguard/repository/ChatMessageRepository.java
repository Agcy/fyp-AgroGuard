package com.api.agroguard.repository;

import com.api.agroguard.model.ChatMessageDO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessageDO, String> {
    List<ChatMessageDO> findByChatRoomId(String chatRoomId);
    List<ChatMessageDO> findByChatRoomIdOrderByTimestampDesc(String chatRoomId);
}
