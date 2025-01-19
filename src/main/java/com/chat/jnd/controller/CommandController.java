package com.chat.jnd.controller;

import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.entity.Message;
import com.chat.jnd.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommandController {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatService chatService;

    @Autowired
    public CommandController(KafkaTemplate<String, Message> kafkaTemplate, SimpMessageSendingOperations messagingTemplate, ChatService chatService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public void send(@RequestBody Message message) {
        kafkaTemplate.send("messaging", message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    @PostMapping
    public ChatResponse createChat(@RequestBody ChatCreateRequest request) {
        System.out.println("create chat");
       return chatService.save(request);
    }
}
