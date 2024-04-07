package com.api.agroguard.service;

import com.api.agroguard.dto.ChatMessageDTO;
import com.api.agroguard.entity.ChatMsgRequest;
import com.api.agroguard.model.ChatMessageDO;
import com.api.agroguard.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private static final Logger log = LoggerFactory.getLogger(ChatMessageService.class);


    @Autowired
    public ChatMessageService(ChatMessageRepository repository) {
        this.repository = repository;
    }

    public ChatMessageDO save(ChatMessageDO chatMessage) {
        try {
            chatMessage.setTimestamp(LocalDateTime.now());
            return repository.save(chatMessage);
        } catch (Exception e) {
            // 处理异常，例如记录日志或抛出自定义异常
            log.error("Error saving chat message", e);
            throw e;
        }
    }

    // 添加缺失的方法
    public List<ChatMessageDO> findMessagesByRoomId(String roomId) {
        return repository.findByChatRoomIdOrderByTimestampDesc(roomId);
    }
}
