package com.chat.jnd.service;

import com.chat.jnd.BaseTest;
import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import com.chat.jnd.mapper.MessageMapper;
import com.chat.jnd.repository.MessageRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest extends BaseTest {

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageRepository messageRepo;

    @Mock
    private ChatService chatService;

    @Mock
    private TokenService tokenService;

    @Mock
    private MessageMapper messageMapper;

    @DisplayName("Test Save New Message")
    @Test
    void save() {
        given(chatService.getCurrentChatByToken(anyString())).willReturn(chat);
        given(messageRepo.save(any(Message.class))).willReturn(message);

        Message messageFromService = messageService.save(message);

        verify(chatService).getCurrentChatByToken(anyString());
        verify(messageRepo).save(any(Message.class));


        assertThat(messageFromService).isNotNull();
        assertEquals(messageFromService.getSenderToken(), message.getSenderToken());
        assertEquals(messageFromService.getType(), message.getType());
        assertEquals(messageFromService.getContent(), message.getContent());
    }

    @DisplayName("Test Find All Messages For Current User By Token")
    @Test
    void findAllMessagesForCurrentUserByToken() {
        given(tokenService.resolveToken(any(HttpServletRequest.class))).willReturn(TOKEN_1_PLAYER);
        given(messageRepo.findMessagesByParams(anyString(), anyInt(), anyInt())).willReturn(List.of(message));
        given(messageMapper.messageToMessageDto(any(Message.class))).willReturn(messageDtos.getFirst());

        List<MessageDto> messagesFromService = messageService.findAllMessagesForCurrentUserByToken(mockRequestWithToken,1,1);

        verify(tokenService).resolveToken(any(HttpServletRequest.class));
        verify(messageRepo).findMessagesByParams(anyString(), anyInt(), anyInt());
        verify(messageMapper).messageToMessageDto(any(Message.class));

        assertThat(messagesFromService).isNotNull();
        assertEquals(messagesFromService.getFirst().getSenderToken(), message.getSenderToken());
        assertEquals(messagesFromService.getFirst().getType(), message.getType());
        assertEquals(messagesFromService.getFirst().getContent(), message.getContent());
    }
}