package com.api.agroguard.controller;

import com.api.agroguard.entity.ChatMsgRequest;
import com.api.agroguard.model.ChatMessageDO;
import com.api.agroguard.model.ChatRoomDO;
import com.api.agroguard.service.ChatMessageService;
import com.api.agroguard.service.ChatRoomService;
import com.api.agroguard.service.KafkaMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ChatController {
    private final KafkaMessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public ChatController(KafkaMessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMsgRequest chatMsgRequest, Principal principal) {
        try {
            boolean isValidUser = validateUser(chatMsgRequest.getSenderId(), chatMsgRequest.getChatRoomId());
            ChatMessageDO chatMessage = new ChatMessageDO();
            chatMessage.setChatRoomId(chatMsgRequest.getChatRoomId());
            chatMessage.setSenderId(chatMsgRequest.getSenderId());
            chatMessage.setContent(chatMsgRequest.getContent());
            chatMessage.setTimestamp(LocalDateTime.now());

            chatMessageService.save(chatMessage);
            messageService.sendMessage("chat-topic", chatMessage);

            // 动态目的地
            messagingTemplate.convertAndSend("/topic/" + chatMsgRequest.getChatRoomId(), chatMessage);
        } catch (Exception e) {
            // 错误处理，可以根据需要发送不同的错误信息
            messagingTemplate.convertAndSend("/topic/errors", "An error occurred.");
        }
    }

    private boolean validateUser(String userId, String chatRoomId) {
        Optional<ChatRoomDO> chatRoomOptional = chatRoomService.findChatRoomById(chatRoomId);
        if (chatRoomOptional.isEmpty()) {
            return false; // 聊天室不存在
        }
        ChatRoomDO chatRoom = chatRoomOptional.get();
        List<String> participants = chatRoom.getParticipants();
        return participants.contains(userId); // 检查userId是否在参与者列表中
    }
}
