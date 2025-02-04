package com.chat.jnd.controller;

import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import com.chat.jnd.errorhandling.ApiErrorValidation;
import com.chat.jnd.service.ChatService;
import com.chat.jnd.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Chat Controller")
public class ChatHTTPController {
    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    private final ChatService chatService;
    private final MessageService messageService;

    @Value("${chat.topic}")
    private String topic;

    @Autowired
    public ChatHTTPController(KafkaTemplate<String, Message> kafkaTemplate, SimpMessageSendingOperations messagingTemplate, ChatService chatService, MessageService messageService) {
        this.kafkaTemplate = kafkaTemplate;
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @Operation(summary = "Send Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The  message send to the /topic/public "),
            @ApiResponse(responseCode = "422", description = "Validation Error")})
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public void send(@RequestBody @Valid Message message) {
        kafkaTemplate.send(topic, message);
        messagingTemplate.convertAndSend("/topic/public", message);
    }

    @Operation(summary = "Create Chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The chat is created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class)) }),
            @ApiResponse(responseCode = "422", description = "Validation Error",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorValidation.class)))})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ChatResponse createChat(@RequestBody @Valid ChatCreateRequest request) {

       ChatResponse chatResponse = chatService.save(request);

       return chatResponse;
    }

    @Operation(summary = "List Messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The list messages",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageDto.class))) }),
            @ApiResponse(responseCode = "422", description = "Validation Error",
                    content = @Content (mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorValidation.class)))})
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> listMessages(@RequestParam(value = "limit", required = true, defaultValue = "10") Integer limit,
                                         @RequestParam(value = "lastId", required = false, defaultValue = "0") Integer lastId,
                                         HttpServletRequest request) {

        return messageService.findAllMessagesForCurrentUserByToken(request, limit, lastId);
    }
}
