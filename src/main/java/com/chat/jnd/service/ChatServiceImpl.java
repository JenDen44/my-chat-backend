package com.chat.jnd.service;

import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepo;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepo) {
        this.chatRepo = chatRepo;
    }

    @Override
    public Chat get(Integer id) {
        return chatRepo.findById(id).orElseThrow
                (() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "GameInfo with id is not found in DB by id " + id)
                );
    }

    @Override
    public ChatResponse save(ChatCreateRequest request) {

        Chat chat = Chat.builder()
                .tokens(request.getTokens())
                .build();

        chat = chatRepo.save(chat);

        return ChatResponse.builder()
                .id(chat.getId())
                .tokens(chat.getTokens())
                .build();

    }

    @Override
    public Chat getCurrentChatByToken(String token) {
        return chatRepo.findByToken(token).orElseThrow
                (() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Chat is not found with token " +  token)
                );
    }
}

