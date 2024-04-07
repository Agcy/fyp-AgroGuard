package com.api.agroguard.service;

import com.api.agroguard.model.ChatRoomDO;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    List<ChatRoomDO> findChatRoomsByParticipantId(String participantId);
    ChatRoomDO getOrCreateChatRoom(ChatRoomDO chatRoom);
    ChatRoomDO createChatRoom(ChatRoomDO chatRoomDO);
    List<ChatRoomDO> findChatRoomsByParticipantIdSorted(String participantId);
    void updateChatRoomLastUpdated(String chatRoomId);
    Optional<ChatRoomDO> findChatRoomById(String chatRoomId);
}
