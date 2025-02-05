package com.chat.jnd.service;

import com.chat.jnd.BaseTest;
import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.repository.ChatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest extends BaseTest {

    @InjectMocks
    private ChatServiceImpl chatService;

    @Mock
    private ChatRepository chatRepo;

    @DisplayName("Test Get Chat By ID")
    @Test
    void get() {
        given(chatRepo.findById(anyInt())).willReturn(Optional.of(chat));

        Chat chatFromService = chatService.get(chat.getId());

        verify(chatRepo).findById(anyInt());
        assertThat(chatFromService).isNotNull();
    }

    @DisplayName("Test Save Chat")
    @Test
    void save() {
        given(chatRepo.save(any(Chat.class))).willReturn(chat);

        ChatResponse responseFromService = chatService.save(request);

        verify(chatRepo).save(any(Chat.class));
        assertThat(responseFromService).isNotNull();
    }

    @DisplayName("Test Get Current Chat By Token")
    @Test
    void getCurrentChatByToken() {
        given(chatRepo.findByToken(anyString())).willReturn(Optional.of(chat));

        Chat chatFromService = chatService.getCurrentChatByToken(chat.getTokens().getFirst());

        verify(chatRepo).findByToken(anyString());
        assertThat(chatFromService).isNotNull();
        assertEquals(chatFromService.getTokens(), chat.getTokens());
    }
}