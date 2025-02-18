package com.chat.jnd.service;


import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatRequest;
import com.chat.jnd.entity.ChatResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ChatService {

    public Chat get(Integer id);

    public ChatResponse save(ChatRequest chat);

    public Chat getCurrentChatByToken(String token);

    void deleteChatByToken(List<String> tokens);
}
