package com.chat.jnd.controller;

import com.chat.jnd.entity.Message;
import com.chat.jnd.service.MessageService;
import com.chat.jnd.service.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChatJMSController {

    private final SenderService sender;
    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public ChatJMSController(SenderService sender, SimpMessageSendingOperations messagingTemplate, MessageService messageService) {
        this.sender = sender;
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @MessageMapping("/chat.send-message")
    public void sendMessage(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        chatMessage.setSessionId(headerAccessor.getSessionId());
        sender.send("messaging", chatMessage);

        log.info("Sending message to /topic/public: " + chatMessage);

        messageService.save(chatMessage);

        messagingTemplate.convertAndSend("/topic/public", chatMessage);

        log.info("Message sent to /topic/public: " + chatMessage);
    }

    @MessageMapping("/chat.add-user")
    @SendTo("/topic/public")
    public Message addPlayer(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        log.info("chat.add-user, added new user");

        if (headerAccessor.getSessionAttributes() != null) {
            log.info("For headerAccessor set new token session attribute");

            messageService.save(chatMessage);

            headerAccessor.getSessionAttributes().put("token", chatMessage.getSenderToken());
        }

        return chatMessage;
    }
}
