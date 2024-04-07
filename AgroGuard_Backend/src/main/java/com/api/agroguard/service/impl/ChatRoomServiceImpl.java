package com.api.agroguard.service.impl;

import com.api.agroguard.model.ChatRoomDO;
import com.api.agroguard.repository.ChatRoomRepository;
import com.api.agroguard.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public List<ChatRoomDO> findChatRoomsByParticipantId(String participantId) {
        return chatRoomRepository.findByParticipantsContaining(participantId);
    }

    @Override
    public ChatRoomDO getOrCreateChatRoom(ChatRoomDO chatRoom) {
        // 例如，如果你要求聊天室恰好包含请求中指定的所有参与者（不多不少）
        List<ChatRoomDO> existingRooms = chatRoomRepository.findByParticipantsContaining(chatRoom.getParticipants());

        for (ChatRoomDO existingRoom : existingRooms) {
            // 检查找到的聊天室是否恰好包含所有指定的参与者，不多不少
            if (existingRoom.getParticipants().size() == chatRoom.getParticipants().size()
                    && new HashSet<>(existingRoom.getParticipants()).containsAll(chatRoom.getParticipants())) {
                return existingRoom; // 找到了匹配的聊天室，直接返回
            }
        }

        // 没有找到现有的聊天室，创建一个新的
        chatRoom.setGmtCreated(LocalDateTime.now());
        chatRoom.setLastUpdated(LocalDateTime.now());
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public List<ChatRoomDO> findChatRoomsByParticipantIdSorted(String participantId) {
        List<ChatRoomDO> chatRooms = chatRoomRepository.findByParticipantsContainingOrderByLastUpdatedDesc(participantId);
        if (chatRooms == null || chatRooms.isEmpty()) {
            // 对于新用户或者没有加入任何聊天室的用户，返回一个空列表而不是抛出异常
            return new ArrayList<>();
        }
        return chatRooms;
    }

    @Override
    public ChatRoomDO createChatRoom(ChatRoomDO chatRoom) {
        chatRoom.setParticipants(chatRoom.getParticipants());
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public void updateChatRoomLastUpdated(String chatRoomId) {
        Optional<ChatRoomDO> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
        chatRoomOptional.ifPresent(chatRoom -> {
            chatRoom.setLastUpdated(LocalDateTime.now()); // 更新时间
            chatRoomRepository.save(chatRoom);
        });
    }

    @Override
    public Optional<ChatRoomDO> findChatRoomById(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

}
