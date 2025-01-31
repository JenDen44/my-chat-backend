package com.chat.jnd.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Chat Request model")
public class ChatCreateRequest {

    @Schema(description = "tokens",
            example = "[eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxN,DgiLCJpYXQiOjE3Mzc0NzUyODQsIkNvbG9yIjoiXCJ3aG]",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty
    private List<String> tokens;
}
