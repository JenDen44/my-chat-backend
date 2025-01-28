package com.chat.jnd.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "messages")
@Entity
@EqualsAndHashCode(exclude = "chat")
@ToString(exclude = "chat")
public class Message implements Serializable {
    static final long serialVersionUID = -8444096321771764676L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private MessageType type;

    private String content;

    private String senderToken;

    private String sessionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;
}
