package com.chat.jnd.mapper;

import com.chat.jnd.entity.Message;
import com.chat.jnd.entity.MessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto messageToMessageDto(Message message);

    Message messageDtoToMessage(MessageDto message);
}
