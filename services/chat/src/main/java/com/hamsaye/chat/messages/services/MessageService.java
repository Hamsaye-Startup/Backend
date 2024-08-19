package com.hamsaye.chat.messages.services;

import com.hamsaye.chat.converstions.exceptions.NotFoundConversationException;
import com.hamsaye.chat.converstions.models.*;
import com.hamsaye.chat.converstions.repositories.ConversationRepository;
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

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public MessageEntity sendMessage(MessageEntity message, UUID recipientId, boolean startConversation) {

        ConversationEntity conversation;
        if (startConversation) {

            // start new conversation
            conversation = startConversation(message.getSenderId(), recipientId);
        }
        else {

            // find the conversation
            conversation = conversationRepository.findById(message.getConversationId())
                    .orElseThrow(() -> new NotFoundConversationException(message.getConversationId()));
        }

        message.setConversationId(conversation.getUid());
        message.setSendAt(LocalDateTime.now());
        MessageEntity saved = messageRepository.save(message);

        // update the conversation
        updateConversationStats(conversation, saved);
        return saved;
    }

    public Page<MessageEntity> loadMessagesByConversationId(UUID conversationId, Pageable pageable) {
        return messageRepository.findAllByConversationId(conversationId, pageable);
    }

    private ConversationEntity startConversation(UUID senderId, UUID recipientId) {

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

    private ConversationEntity generateConversation(UserEntity sender, UserEntity recipient) {
        return ConversationEntity.builder()
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

    public MessageEntity findMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new NotFoundMessageException(id.toString())); // TODO: implement the exception
    }

    public void deleteMessagesByConversationId(UUID uid) {
        messageRepository.deleteAllByConversationId(uid);
    }
}
