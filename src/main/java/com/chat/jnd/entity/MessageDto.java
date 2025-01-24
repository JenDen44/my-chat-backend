package com.chat.jnd.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Integer id;

    private MessageType type;

    private String content;

    private String senderToken;

}
