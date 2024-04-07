package com.api.agroguard.controller;
import com.api.agroguard.dto.ChatMessageDTO;
import com.api.agroguard.entity.ChatRoomRequest;
import com.api.agroguard.dto.UserDTO;
import com.api.agroguard.entity.ChatMsgRequest;
import com.api.agroguard.model.ChatMessageDO;
import com.api.agroguard.model.ChatRoomDO;
import com.api.agroguard.service.ChatMessageService;
import com.api.agroguard.service.ChatRoomService;
import com.api.agroguard.service.UserService;
import com.api.agroguard.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ChatRoomDO> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest, @AuthenticationPrincipal UserDetailsImpl user) {
        // 从DTO到DO的转换逻辑
        ChatRoomDO chatRoom = new ChatRoomDO();
        chatRoom.setName(chatRoomRequest.getName());
        chatRoom.setParticipants(chatRoomRequest.getParticipants());
        // 可以在这里添加当前用户作为聊天室的创建者或参与者
        chatRoom.setCreator(user.getId()); // 假设你的User实体有getId()方法
        chatRoom.setGmtCreated(LocalDateTime.now());
        chatRoom.setLastUpdated(LocalDateTime.now());

        ChatRoomDO newOrExistingChatRoom = chatRoomService.getOrCreateChatRoom(chatRoom);
        return ResponseEntity.ok(newOrExistingChatRoom);
    }

    @GetMapping("/participant/{participantId}")
    public ResponseEntity<List<ChatRoomDO>> getChatRoomsByParticipant(@PathVariable String participantId) {
        List<ChatRoomDO> chatRooms = chatRoomService.findChatRoomsByParticipantIdSorted(participantId);
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ChatRoomDO> getChatRoomById(@PathVariable String chatRoomId) {
        return chatRoomService.findChatRoomById(chatRoomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{chatRoomId}/messages")
    public ResponseEntity<List<ChatMessageDO>> getChatRoomMessages(@PathVariable String chatRoomId) {
        List<ChatMessageDO> messages = chatMessageService.findMessagesByRoomId(chatRoomId);
        if (messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(messages);
    }

}
