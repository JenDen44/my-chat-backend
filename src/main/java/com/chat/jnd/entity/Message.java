package com.chat.jnd.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
@Entity
public class Message {

        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        private Integer id;

        private MessageType type;

        private String content;

        private String senderToken;

        private String sessionId;

        @ManyToOne
        @JoinColumn(name="chat_id", nullable=false)
        private Chat chat;
}
