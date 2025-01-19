package com.chat.jnd.service;

import com.chat.jnd.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverService.class);
    private final SimpMessageSendingOperations messagingTemplate;
    private final SimpUserRegistry userRegistry;

    public ReceiverService(SimpMessageSendingOperations messagingTemplate, SimpUserRegistry userRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.userRegistry = userRegistry;
    }

    @KafkaListener(topics = "messaging", groupId = "chat")
    public void consume(Message chatMessage) {
        logger.info("Received message from Kafka: " + chatMessage);
        for (SimpUser user : userRegistry.getUsers()) {
            for (SimpSession session : user.getSessions()) {
                if (!session.getId().equals(chatMessage.getSessionId())) {
                    messagingTemplate.convertAndSendToUser(session.getId(), "/topic/public", chatMessage);
                }
            }
        }
    }
}
