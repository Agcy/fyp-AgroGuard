package com.api.agroguard.repository;

import com.api.agroguard.model.ChatRoomDO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoomDO, String> {
    List<ChatRoomDO> findByParticipantsContaining(String participantId);
    List<ChatRoomDO> findByParticipantsContainingOrderByLastUpdatedDesc(String participantId);

    @Query("{ 'participants': { $all: ?0 } }")
    List<ChatRoomDO> findByParticipantsContaining(List<String> participants);
}
