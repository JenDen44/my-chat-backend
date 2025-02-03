package com.chat.jnd.service;

import com.chat.jnd.entity.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.user.SimpUserRegistry;

@ExtendWith(MockitoExtension.class)
class ReceiverServiceTest {

    @InjectMocks
    ReceiverService receiverService;

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @Mock
    private SimpUserRegistry userRegistry;

    @Test
    void consume() {
        receiverService.consume(new Message());
    }
}