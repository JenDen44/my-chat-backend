package com.chat.jnd.controller;

import com.chat.jnd.BaseTest;
import com.chat.jnd.entity.ChatRequest;
import com.chat.jnd.entity.Message;
import com.chat.jnd.service.ChatService;
import com.chat.jnd.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ChatHTTPController.class)
class ChatHTTPControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ChatService chatService;

    @MockitoBean
    private MessageService messageService;

    @MockitoBean
    private  KafkaTemplate<String, Message> kafkaTemplate;

    @MockitoBean
    private  SimpMessageSendingOperations messagingTemplate;


    @DisplayName("Test send message")
    @Test
    void send() throws Exception {
        mvc.perform(post("/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                        .andExpect(status().isOk());

        verify(kafkaTemplate).send(anyString(), any(Message.class));
        verify(messagingTemplate).convertAndSend(anyString(), any(Message.class));
    }

    @DisplayName("Test create chat")
    @Test
    void createChat() throws Exception {

        given(chatService.save(any(ChatRequest.class))).willReturn(response);

        var responseFromController =
                mvc.perform(post("/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)));

        responseFromController
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(response.getId())))
                .andExpect(jsonPath("$.tokens", is(response.getTokens())));

        verify(chatService).save(any(ChatRequest.class));
    }

    @DisplayName("Test list get messages")
    @Test
    void listMessages() throws Exception {
        given(messageService.findAllMessagesForCurrentUserByToken(any(HttpServletRequest.class), anyInt(), anyInt())).willReturn(messageDtos);

        var response = mvc.perform(get("/messages").headers(header));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].id", is(messageDtos.getFirst().getId())))
                .andExpect(jsonPath("$.[0].type", is(messageDtos.getFirst().getType().name())))
                .andExpect(jsonPath("$.[0].content", is(messageDtos.getFirst().getContent())))
                .andExpect(jsonPath("$.[0].senderToken", is(messageDtos.getFirst().getSenderToken())));

        verify(messageService).findAllMessagesForCurrentUserByToken(any(HttpServletRequest.class), anyInt(), anyInt());
    }
}