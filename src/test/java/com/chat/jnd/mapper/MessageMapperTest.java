package com.chat.jnd.mapper;

import com.chat.jnd.BaseTest;
import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageMapperTest extends BaseTest {

    private final MessageMapper messageMapper = new MessageMapperImpl();

    @DisplayName("Test Map Message to MessageDto")
    @Test
    void messageToMessageDto() {
        MessageDto messageFromMapper = messageMapper.messageToMessageDto(message);

        assertEquals(messageFromMapper.getId(), message.getId());
        assertEquals(messageFromMapper.getContent(), message.getContent());
        assertEquals(messageFromMapper.getSenderToken(), message.getSenderToken());
        assertEquals(messageFromMapper.getType(), message.getType());
    }

    @DisplayName("Test Map MessageDto to Message")
    @Test
    void messageDtoToMessage() {
        Message messageFromMapper = messageMapper.messageDtoToMessage(messageDto);

        assertEquals(messageFromMapper.getId(), messageDto.getId());
        assertEquals(messageFromMapper.getContent(), messageDto.getContent());
        assertEquals(messageFromMapper.getSenderToken(), messageDto.getSenderToken());
        assertEquals(messageFromMapper.getType(), messageDto.getType());
    }
}