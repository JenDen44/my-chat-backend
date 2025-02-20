package com.chat.jnd;

import com.chat.jnd.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.List;

public class BaseTest {

    protected Chat chat;
    protected ChatRequest request;
    protected Message message;
    protected MessageDto messageDto;
    protected HttpHeaders header;
    protected ChatResponse response;
    protected MockHttpServletRequest mockRequestWithToken;
    protected MockHttpServletRequest mockRequestWithoutToken;
    protected static final String TOKEN_1_PLAYER = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNDgiLCJpYXQiOjE3Mzc0NzUyODQsIkNvbG9yIjoiXCJ3aGl0ZVwiIn0.qNaIRsDmtr1wYw5CGikhzchV5OBBDiI3mpos4EthHQm_nOTbB9Vpmh35L1TjLOIHIUIiuXv7WNcveYliH9kmMA";
    protected static final String TOKEN_2_PLAYER = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNDgiLCJpYXQiOjE3Mzc0NzUyODQsIkNvbG9yIjoiXCJibGFja1wiIn0.dLUBEP2evJ5QGTFSUco17IVsyQVzMEiXhSJ0LQzFYLM2uGJ4zCL3Che6XggU7f9trGrxy1nk7GBYsuzOI7Upbg";
    protected List<MessageDto> messageDtos;
    protected static  String TOPIC = "messaging";

    @BeforeEach
    public void setup(){
        chat = Chat.builder()
                .id(1)
                .tokens(List.of(TOKEN_1_PLAYER, TOKEN_2_PLAYER))
                .build();

        message = Message.builder()
                .id(1)
                .content("Hello World")
                .type(MessageType.CHAT)
                .senderToken(TOKEN_1_PLAYER)
                .build();

        messageDto = MessageDto.builder()
                .id(1)
                .content("Hello world")
                .senderToken(TOKEN_1_PLAYER)
                .type(MessageType.DISCONNECT)
                .build();

        messageDtos = List.of(MessageDto.builder()
                .id(message.getId())
                .content(message.getContent())
                .type(message.getType())
                .senderToken(message.getSenderToken())
                .build());

        request = ChatRequest.builder()
                .tokens(List.of(TOKEN_1_PLAYER, TOKEN_2_PLAYER))
                .build();

        mockRequestWithToken = new MockHttpServletRequest();
        mockRequestWithToken.addHeader("Authorization", "Bearer " + TOKEN_1_PLAYER);

        mockRequestWithoutToken = new MockHttpServletRequest();

        response = ChatResponse.builder()
                .id(1)
                .tokens(List.of(TOKEN_1_PLAYER, TOKEN_2_PLAYER))
                .build();

        header = new HttpHeaders();
        header.add("Authorization", "Bearer " + TOKEN_1_PLAYER);
    }
}