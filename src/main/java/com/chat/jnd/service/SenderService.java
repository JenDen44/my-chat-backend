package com.chat.jnd.service;

import com.chat.jnd.entity.Message;
import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding;
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation;
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    public SenderService(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @AsyncPublisher(
            operation = @AsyncOperation(
            channelName = "messaging"
            )
    )
    @KafkaAsyncOperationBinding
    public void send(String topic, @Payload Message message) {
        kafkaTemplate.send(topic, message);
    }
}
