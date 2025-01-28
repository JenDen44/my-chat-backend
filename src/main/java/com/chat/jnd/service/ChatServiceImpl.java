package com.chat.jnd.service;

import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.exception.ChatNotFoundException;
import com.chat.jnd.repository.ChatRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
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
        return chatRepo.findById(id).orElseThrow(() -> {
            log.error("Chat with id is not found in DB by id -> " + id);
            return new ChatNotFoundException("Chat with id is not found in DB by id " + id);
        });
    }

    @Override
    public ChatResponse save(ChatCreateRequest request) {
        log.info("save chat -> request object -> " +  request);

        Chat chat = Chat.builder()
                .tokens(request.getTokens())
                .build();

        chat = chatRepo.save(chat);

        log.info("chat saved to DB with generated id -> " + chat.getId());

        return ChatResponse.builder()
                .id(chat.getId())
                .tokens(chat.getTokens())
                .build();

    }

    @Override
    public Chat getCurrentChatByToken(String token) {
        return chatRepo.findByToken(token).orElseThrow(() -> {
        log.error("Chat is not found in DB by Token -> " + token);
        return new ChatNotFoundException("Chat is not found in DB by Token -> " + token);
    });
    }
}

