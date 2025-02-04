package com.chat.jnd.service;

import com.chat.jnd.entity.Chat;
import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import com.chat.jnd.mapper.MessageMapper;
import com.chat.jnd.repository.MessageRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepo;
    private final ChatServiceImpl chatService;
    private final TokenService tokenService;
    private final MessageMapper messageMapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepo, ChatServiceImpl chatService, TokenService tokenService, MessageMapper messageMapper) {
        this.messageRepo = messageRepo;
        this.chatService = chatService;
        this.tokenService = tokenService;
        this.messageMapper = messageMapper;
    }

    @Override
    public Message save(Message message) {
        log.info("save message -> request message -> " +  message);

        Chat currentChat = chatService.getCurrentChatByToken(message.getSenderToken());
        message.setChat(currentChat);
        Message savedMessage = messageRepo.save(message);

        log.info("message saved to DB with generated id -> " + message.getId());

        return savedMessage;
    }

    @Override
    public List<MessageDto> findAllMessagesForCurrentUserByToken(HttpServletRequest request, Integer limit, Integer lastId) {
        log.info("findAll Messages ForCurrent User By Token (limit) -> " + limit + " (last id) -> " + lastId);

        String token = tokenService.resolveToken(request);
        List<Message> messages =  lastId == 0 ?
                messageRepo.findMessagesByToken(token, limit) :
                messageRepo.findMessagesByParams(token, limit, lastId);

        log.info("findAll Messages ForCurrent User By Token (limit) -> " + limit + " (last id) -> " + lastId);
        log.info("found messages : " + messages);

        return messages
                .stream()
                .map(messageMapper::messageToMessageDto)
                .collect(Collectors.toList());
    }
}

