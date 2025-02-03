package com.chat.jnd.service;

import com.chat.jnd.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest extends BaseTest {

    @InjectMocks
    TokenService tokenService;

    @DisplayName("Test resolve token")
    @Test
    void resolveToken() {
        String bearerToken = tokenService.resolveToken(mockRequestWithToken);

        assertThat(bearerToken).isNotNull();
        assertEquals(bearerToken,TOKEN_1_PLAYER);
    }

    @DisplayName("Test resolve token with error")
    @Test
    void resolveTokenWithError() {
        String expectedMessage = "Token is not specified in Authorization header";

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            tokenService.resolveToken(mockRequestWithoutToken);
        });

        assertThat(exception.getMessage().endsWith(expectedMessage));
    }
}