package com.hamsaye.chat.converstions.services;

import com.hamsaye.chat.converstions.exceptions.NotFoundConversationException;
import com.hamsaye.chat.converstions.models.ConversationEntity;
import com.hamsaye.chat.converstions.repositories.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for managing conversations.
 * This service handles operations such as finding and deleting conversations
 * by interacting with the {@link ConversationRepository}.
 *
 * <p>It integrates with the {@link ConversationRepository} to perform its tasks.
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ConversationService {

    /**
     * Repository for accessing conversation data.
     *
     * @see com.hamsaye.chat.converstions.repositories.ConversationRepository
     */
    private final ConversationRepository conversationRepository;

    /**
     * Deletes a conversation.
     *
     * @param conversation The {@link ConversationEntity} to be deleted.
     * @return The deleted {@link ConversationEntity}.
     */
    public ConversationEntity deleteConversation(ConversationEntity conversation) {

        // Delete the conversation
        conversationRepository.delete(conversation);
        return conversation;
    }

    /**
     * Finds a conversation by its unique identifier.
     *
     * @param uid The unique identifier of the conversation to be found.
     * @return The {@link ConversationEntity} with the specified unique identifier.
     * @throws NotFoundConversationException If no conversation with the specified ID is found.
     */
    public ConversationEntity findConversationByUid(UUID uid) {
        return conversationRepository.findById(uid)
                .orElseThrow(() -> new NotFoundConversationException(uid.toString()));
    }

    /**
     * Finds all conversations associated with a specified user.
     *
     * @param uid The unique identifier of the user whose conversations are to be retrieved.
     * @return A list of {@link ConversationEntity} objects associated with the specified user.
     */
    public List<ConversationEntity> findAllConversationsByUserUid(UUID uid) {
        return conversationRepository.findAllByUsersUid(uid);
    }
}
