package com.chat.jnd.service;

import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatRequest;
import com.chat.jnd.entity.ChatResponse;
import com.chat.jnd.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepo;
    private final MessageService messageService;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepo, @Lazy MessageService messageService) {
        this.chatRepo = chatRepo;
        this.messageService = messageService;
    }

    @Override
    public Chat get(Integer id) {
        return chatRepo.findById(id).orElseThrow
                (() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "GameInfo with id is not found in DB by id " + id)
                );
    }

    @Override
    public ChatResponse save(ChatRequest request) {

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

    @Override
    public void deleteChatByToken(List<String> tokens) {
        if (tokens.isEmpty()) {
            return;
        }

        List<Chat> chatsByTokens = new ArrayList<>();
        tokens.stream().forEach(token -> chatsByTokens.add(getCurrentChatByToken(token)));

        if (!chatsByTokens.isEmpty()) {
            messageService.deleteMessagesByChats(chatsByTokens);
            chatRepo.deleteAll(chatsByTokens);
        }
    }
}

