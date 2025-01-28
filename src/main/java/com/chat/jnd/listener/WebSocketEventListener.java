package com.chat.jnd.listener;

import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageType;
import com.chat.jnd.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String token = (String) headerAccessor.getSessionAttributes().get("token");

        if (token != null) {
            log.info("User Disconnected: " + token);
            Message chatMessage = new Message();
            chatMessage.setType(MessageType.DISCONNECT);
            chatMessage.setSenderToken(token);
            messageService.save(chatMessage);
            log.info("Message is sent to /topic/public " + chatMessage);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
