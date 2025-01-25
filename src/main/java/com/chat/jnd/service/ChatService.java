package com.chat.jnd.service;


import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.ChatCreateRequest;
import com.chat.jnd.entity.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {

    public Chat get(Integer id);

    public ChatResponse save(ChatCreateRequest chat);

    public Chat getCurrentChatByToken(String token);
}
