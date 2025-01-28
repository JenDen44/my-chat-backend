package com.chat.jnd.controller;

import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import com.chat.jnd.service.ChatService;
import com.chat.jnd.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatHTTPController {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatService chatService;
    private final MessageService messageService;

    @Autowired
    public ChatHTTPController(KafkaTemplate<String, Message> kafkaTemplate, SimpMessageSendingOperations messagingTemplate, ChatService chatService, MessageService messageService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestBody @Valid Message message) {
        kafkaTemplate.send("messaging", message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ChatResponse createChat(@RequestBody @Valid ChatCreateRequest request) {

       ChatResponse chatResponse = chatService.save(request);

       return chatResponse;
    }

    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> listMessages(@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                                         @RequestParam(value = "lastId", required = false, defaultValue = "0") Integer lastId,
                                         HttpServletRequest request) {

        return messageService.findAllMessagesForCurrentUserByToken(request, limit, lastId);
    }
}
