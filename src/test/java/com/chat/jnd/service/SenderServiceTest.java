package com.chat.jnd.service;

import com.chat.jnd.entity.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class SenderServiceTest {

    @InjectMocks
    SenderService senderService;

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Test
    void send() {
        senderService.send("messaging", new Message());
    }
}