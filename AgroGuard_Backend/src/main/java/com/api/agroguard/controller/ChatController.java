package com.api.agroguard.controller;

import com.api.agroguard.entity.*;
import com.api.agroguard.model.ChatMessageDO;
import com.api.agroguard.model.ChatRoomDO;
import com.api.agroguard.service.ChatMessageService;
import com.api.agroguard.service.ChatRoomService;
import com.api.agroguard.service.KafkaMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/app")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    private final ArrayList<User> users = new ArrayList<>();
    private final HashMap<String, Group> groups = new HashMap<>() {{
        put("public", new Group("public", "Public", new HashSet<>()));
    }};
//    private final KafkaMessageService messageService;
//
//    @Autowired
//    private ChatRoomService chatRoomService;
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;

//    public ChatController(KafkaMessageService messageService) {
//        this.messageService = messageService;
//    }

//    @MessageMapping("/chat.sendMessage")
//    public void sendMessage(@Payload ChatMsgRequest chatMsgRequest, Principal principal) {
//        try {
//            boolean isValidUser = validateUser(chatMsgRequest.getSenderId(), chatMsgRequest.getChatRoomId());
//            ChatMessageDO chatMessage = new ChatMessageDO();
//            chatMessage.setChatRoomId(chatMsgRequest.getChatRoomId());
//            chatMessage.setSenderId(chatMsgRequest.getSenderId());
//            chatMessage.setContent(chatMsgRequest.getContent());
//            chatMessage.setTimestamp(LocalDateTime.now());
//
//            chatMessageService.save(chatMessage);
//            messageService.sendMessage("chat-topic", chatMessage);
//
//            // 动态目的地
//            messagingTemplate.convertAndSend("/topic/" + chatMsgRequest.getChatRoomId(), chatMessage);
//        } catch (Exception e) {
//            // 错误处理，可以根据需要发送不同的错误信息
//            messagingTemplate.convertAndSend("/topic/errors", "An error occurred.");
//        }
//    }
//
//    private boolean validateUser(String userId, String chatRoomId) {
//        Optional<ChatRoomDO> chatRoomOptional = chatRoomService.findChatRoomById(chatRoomId);
//        if (chatRoomOptional.isEmpty()) {
//            return false; // 聊天室不存在
//        }
//        ChatRoomDO chatRoom = chatRoomOptional.get();
//        List<String> participants = chatRoom.getParticipants();
//        return participants.contains(userId); // 检查userId是否在参与者列表中
//    }

    @PostMapping("/register")
    public ResponseEntity<String> RegisterUser(@RequestBody User user) {
        if (users.contains(user)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("There is already an user with this username in the chat.");
        }
        users.add(user);
        groups.get("public").AddMember(simpMessagingTemplate, user);
        return ResponseEntity.ok().build();
    }

    /**
     * Remove a user
     * @param user The user to remove
     */
    @PostMapping("/unregister")
    public ResponseEntity<String> UnregisterUser(@RequestBody User user) {
        RemoveUserFromGroups(user);
        users.remove(user);
        return ResponseEntity.ok().build();
    }

    /**
     * Add a new group
     * @param request The new group's information
     * @return The group's id
     */
    @RequestMapping("/new_group")
    @ResponseBody
    public String CreateGroup(@RequestBody CreateGroupRequest request) {
        String id = UUID.randomUUID().toString();
        HashSet<User> members = new HashSet<>();
        request.getUsernames().forEach(username -> {
            members.add(new User(username));
        });

        groups.put(id, new Group(id, request.getGroupName(), members));
        System.out.println(groups.toString());
        return id;
    }

    /**
     * Get all users
     * @return All users in the server
     */
    @RequestMapping("/users")
    @ResponseBody
    public ArrayList<User> GetUsers() {
        return users;
    }

    /**
     * Get all groups that a user is in
     * @return All groups of a user
     */
    @RequestMapping("/get_groups/user")
    @ResponseBody
    public ArrayList<Group> GetGroupsOfUser(@RequestBody User user) {
        ArrayList<Group> result = new ArrayList<>();
        groups.forEach((key, group) -> {
            if (group.ContainsUser(user)) {
                result.add(group);
            }
        });
        return result;
    }

    /**
     * Given a group id return the group
     * @return The group associated with the id
     */
    @RequestMapping("/get_group")
    @ResponseBody
    public Group GetGroupById(@RequestBody String id) {
        return groups.get(id.substring(0, id.length() - 1));
    }

    /**
     * Function to handle a private message sent
     * @param message The message sent
     */
    @MessageMapping("/private-message")
    public Message ReceivePrivateMessage(@Payload Message message) {
        try {
            Group group = groups.get(message.getReceiverId());
            System.out.println("Received message: " + message);
            group.SendMessageToMembers(simpMessagingTemplate, message);
            System.out.println("Group: " + group);
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Remove a specific user from all groups
     * @param user The user to remove
     */
    private void RemoveUserFromGroups(User user) {
        ArrayList<String> toDeleted = new ArrayList<>();
        groups.forEach((key, group) -> {
            group.RemoveMember(simpMessagingTemplate, user);
            if (group.getMembers().size() == 0 && !Objects.equals(key, "public")) {
                toDeleted.add(key);
            }
        });
        toDeleted.forEach(groups::remove);
    }
}
