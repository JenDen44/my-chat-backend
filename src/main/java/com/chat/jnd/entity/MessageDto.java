package com.chat.jnd.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "MessageDto model")
public class MessageDto {

    private Integer id;

    @Schema(description = "type", example = "CHAT|CONNECT|DISCONNECT",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private MessageType type;

    @Schema(description = "content", example = "Hello friend!",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String content;

    @Schema(description = "sender Token", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxN",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String senderToken;

}
