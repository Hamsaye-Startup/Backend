package com.hamsaye.chat.messages.services;

import com.hamsaye.chat.converstions.exceptions.NotFoundConversationException;
import com.hamsaye.chat.converstions.models.*;
import com.hamsaye.chat.converstions.repositories.ConversationRepository;
import com.hamsaye.chat.messages.exceptions.ConversationAlreadyExistsException;
import com.hamsaye.chat.messages.exceptions.NotFoundMessageException;
import com.hamsaye.chat.messages.models.MessageDTO;
import com.hamsaye.chat.messages.models.MessageEntity;
import com.hamsaye.chat.messages.repositories.MessageRepository;
import com.hamsaye.chat.users.exceptions.NotFoundUserException;
import com.hamsaye.chat.users.models.UserEntity;
import com.hamsaye.chat.users.models.UserRef;
import com.hamsaye.chat.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service class responsible for handling message operations.
 * <p>
 * This service manages the sending of messages, loading messages by conversation ID, and handling
 * conversations between users. It also provides methods for starting new conversations, updating
 * conversation statistics, and deleting messages.
 * </p>
 *
 * @author Pouria Ghafarbeigi
 * @version 1.0
 */
@Service
@RequiredArgsConstructor
public class MessageService {

    /**
     * Repository for message-related operations.
     * @see com.hamsaye.chat.messages.repositories.MessageRepository
     */
    private final MessageRepository messageRepository;

    /**
     * Repository for conversation-related operations.
     * @see com.hamsaye.chat.converstions.repositories.ConversationRepository
     */
    private final ConversationRepository conversationRepository;

    /**
     * Repository for user-related operations.
     * @see com.hamsaye.chat.users.repositories.UserRepository
     */
    private final UserRepository userRepository;

    /**
     * Sends a message and starts a new conversation if specified.
     * <p>
     * If {@code startConversation} is {@code true}, a new conversation is started between the sender and
     * recipient. Otherwise, the existing conversation is retrieved. The message is then saved and the
     * conversation statistics are updated.
     * </p>
     *
     * @param message the message to be sent
     * @param recipientId the ID of the recipient
     * @param startConversation flag indicating whether to start a new conversation
     * @return the saved {@link MessageEntity}
     * @throws NotFoundConversationException if the conversation is not found
     * @throws ConversationAlreadyExistsException if a conversation already exists between the sender and recipient
     * @throws NotFoundUserException if either the sender or recipient is not found
     * @since 1.0
     */
    public MessageEntity sendMessage(MessageEntity message, UUID recipientId, boolean startConversation) {

        ConversationEntity conversation;
        if (startConversation) {

            // start new conversation
            conversation = startConversation(message.getSenderId(), recipientId);
        }
        else {

            // find the conversation
            conversation = conversationRepository.findById(message.getConversationId())
                    .orElseThrow(() -> new NotFoundConversationException(
                            String.format(
                                    "conversationId: '%s', senderId: '%s'",
                                    message.getConversationId().toString(),
                                    message.getSenderId().toString()
                            )
                    ));
        }

        message.setConversationId(conversation.getUid());
        message.setSendAt(LocalDateTime.now());
        MessageEntity saved = messageRepository.save(message);

        // update the conversation
        updateConversationStats(conversation, saved);
        return saved;
    }

    /**
     * Loads messages for a specific conversation ID.
     * <p>
     * This method retrieves a paginated list of messages associated with the given conversation ID.
     * </p>
     *
     * @param conversationId the unique identifier of the conversation
     * @param pageable pagination information
     * @return a {@link Page} of {@link MessageEntity} objects
     * @since 1.0
     */
    public Page<MessageEntity> loadMessagesByConversationId(UUID conversationId, Pageable pageable) {
        return messageRepository.findAllByConversationId(conversationId, pageable);
    }

    /**
     * Starts a new conversation between two users.
     * <p>
     * This method checks if a conversation already exists between the two users. If not, it creates a new
     * conversation with the provided sender and recipient. If either user is not found, an exception is thrown.
     * </p>
     *
     * @param senderId the ID of the sender
     * @param recipientId the ID of the recipient
     * @return the newly created {@link ConversationEntity}
     * @throws ConversationAlreadyExistsException if a conversation already exists between the sender and recipient
     * @throws NotFoundUserException if either the sender or recipient is not found
     * @since 1.0
     */
    private ConversationEntity startConversation(UUID senderId, UUID recipientId) {

        // check the duplicate conversation
        ConversationEntity exists = conversationRepository.findByStarterAndContinuator(senderId, recipientId)
                .orElse(null);
        if (exists != null) {
            throw new ConversationAlreadyExistsException(String.format("senderId: '%s', recipientId: '%s'", senderId, recipientId));
        }

        // find the users
        UserEntity sender = userRepository.findById(senderId)
                .orElse(null);
        UserEntity recipient = userRepository.findById(recipientId)
                .orElse(null);

        if (sender != null && recipient != null) {
            // generate a conversation
            ConversationEntity startedConversation = generateConversation(sender, recipient);
            return conversationRepository.save(startedConversation);
        }
        else {
            throw new NotFoundUserException(String.format(
                    "can't start conversation with senderId '%s' and recipientId '%s'",
                    senderId,
                    recipientId
            ));
        }
    }

    /**
     * Creates a new conversation entity between two users.
     * <p>
     * This method creates a new {@link ConversationEntity} with the provided sender and recipient details.
     * </p>
     *
     * @param sender the sender user entity
     * @param recipient the recipient user entity
     * @return the newly created {@link ConversationEntity}
     * @since 1.0
     */
    private ConversationEntity generateConversation(UserEntity sender, UserEntity recipient) {
        return ConversationEntity.builder()
                .uid(UUID.randomUUID())
                .users(UserReferenceCollection.builder()
                        .starter(UserRef.builder()
                                .uid(sender.getUid())
                                .name(sender.getFirstname() + " " + sender.getLastname())
                                .build())
                        .continuator(UserRef.builder()
                                .uid(recipient.getUid())
                                .name(recipient.getFirstname() + " " + recipient.getLastname())
                                .build())
                        .build())
                .initiatedBy(InitiationByEnum.USER_INITIATED)
                .build();
    }

    /**
     * Updates the statistics of a conversation after a message is sent.
     * <p>
     * This method updates the conversation's statistics with the latest message details and saves the updated
     * conversation entity.
     * </p>
     *
     * @param conversation the {@link ConversationEntity} to be updated
     * @param message the {@link MessageEntity} that was sent
     * @since 1.0
     */
    private void updateConversationStats(ConversationEntity conversation, MessageEntity message) {

        // set the conversationStats
        conversation.setConversationStats(ConversationStats.builder()
                .eventTiming(ConversationEventTiming.builder()
                        .lastMessageTime(message.getSendAt())
                        .build())
                .stats(ConversationEventStats.builder()
                        .lastMessageDto(MessageDTO.builder()
                                .id(message.getId())
                                .content(message.getContent())
                                .build())
                        .build())
                .build());

        // update the conversation
        conversationRepository.deleteById(conversation.getUid());
        conversationRepository.save(conversation);
    }

    /**
     * Finds a message by its unique identifier.
     * <p>
     * This method retrieves a {@link MessageEntity} by its ID. If the message is not found, a
     * {@link NotFoundMessageException} is thrown.
     * </p>
     *
     * @param id the unique identifier of the message
     * @return the {@link MessageEntity} with the specified ID
     * @throws NotFoundMessageException if the message is not found
     * @since 1.0
     */
    public MessageEntity findMessageById(String id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundMessageException(id));
    }

    /**
     * Deletes all messages associated with a specific conversation ID.
     * <p>
     * This method removes all messages related to the given conversation ID from the repository.
     * </p>
     *
     * @param uid the unique identifier of the conversation
     * @since 1.0
     */
    public void deleteMessagesByConversationId(UUID uid) {
        messageRepository.deleteAllByConversationId(uid);
    }
}
