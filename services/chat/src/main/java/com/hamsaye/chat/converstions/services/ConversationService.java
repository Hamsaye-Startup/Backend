package com.hamsaye.chat.converstions.services;

import com.hamsaye.chat.converstions.exceptions.NotFoundConversationException;
import com.hamsaye.chat.converstions.models.ConversationEntity;
import com.hamsaye.chat.converstions.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public ConversationEntity deleteConversation(ConversationEntity conversation) {

        // delete the previous one
        conversationRepository.delete(conversation);
        return conversation;
    }

    public ConversationEntity findConversationByUid(UUID uid) {
        return conversationRepository.findById(uid)
                .orElseThrow(() -> new NotFoundConversationException(uid.toString()));
    }

    public List<ConversationEntity> findAllConversationsByUserUid(UUID uid) {
        return conversationRepository.findAllByUsersUid(uid);
    }
}
