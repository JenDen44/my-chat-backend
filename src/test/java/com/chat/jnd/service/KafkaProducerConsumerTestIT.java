package com.chat.jnd.service;

import com.chat.jnd.BaseTest;
import com.chat.jnd.entity.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
class KafkaProducerConsumerTestIT extends BaseTest {

    @Autowired
    SenderService senderService;

    @Autowired
    ReceiverService receiverService;


    @DisplayName("Test send message by producer and receive by consumer")
    @Test
    void sendAndReceive() {
        senderService.send(TOPIC, message);

        await().until(() -> receiverService.getArrivedMessage() != null);

        Message messageFromProducer = receiverService.getArrivedMessage();

        assertEquals(messageFromProducer.getContent(), message.getContent());
        assertEquals(messageFromProducer.getSenderToken(), message.getSenderToken());
        assertEquals(messageFromProducer.getType(), message.getType());
    }
}