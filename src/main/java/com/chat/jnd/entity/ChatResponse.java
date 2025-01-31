package com.chat.jnd.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Chat Response model")
public class ChatResponse {

    @Schema(description = "chat id", example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "tokens",
            example = "[eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxN,DgiLCJpYXQiOjE3Mzc0NzUyODQsIkNvbG9yIjoiXCJ3aG]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    List<String> tokens;
}
