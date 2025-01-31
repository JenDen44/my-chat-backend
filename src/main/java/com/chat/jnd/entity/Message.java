package com.chat.jnd.entity;

import com.chat.jnd.validator.EnumNamePattern;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Message model")
public class Message implements Serializable {
    static final long serialVersionUID = -8444096321771764676L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Schema(description = "type", example = "CHAT|CONNECT|DISCONNECT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @EnumNamePattern(regexp = "CHAT|CONNECT|DISCONNECT")
    private MessageType type;

    @Schema(description = "content", example = "Hello friend!",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "sender Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxN",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty
    private String senderToken;

    @JsonIgnore
    private String sessionId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="chat_id", nullable=false)
    private Chat chat;
}
