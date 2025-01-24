package com.chat.jnd.service;

import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    public Message get(Integer id);

    public Message save(Message message);

    List<MessageDto> findAllMessagesForCurrentUserByToken(HttpServletRequest request, Integer limit, Integer lastId);
}