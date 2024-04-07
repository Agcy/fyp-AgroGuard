package com.api.agroguard.service;

import com.api.agroguard.model.ChatMessageDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService {
    private final KafkaTemplate<String, ChatMessageDO> kafkaTemplate;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public KafkaMessageService(KafkaTemplate<String, ChatMessageDO> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, ChatMessageDO message) {
        kafkaTemplate.send(topic, message);
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(ChatMessageDO message) {
        chatMessageService.save(message);
        String destination = "/topic/chatrooms/" + message.getChatRoomId();
        messagingTemplate.convertAndSend(destination, message);
    }
}
