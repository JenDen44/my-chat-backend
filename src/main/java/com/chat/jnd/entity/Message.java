package com.chat.jnd.entity;

import com.chat.jnd.validator.EnumNamePattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @EnumNamePattern(regexp = "CHAT|CONNECT|DISCONNECT")
    private MessageType type;

    private String content;

    @NotEmpty
    private String senderToken;

    private String sessionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;
}
