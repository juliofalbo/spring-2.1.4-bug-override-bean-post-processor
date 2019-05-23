package com.example.demo;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SomeListener {

    private static final Logger log = LoggerFactory.getLogger(SomeListener.class);

    @RabbitListener(containerFactory = "containerFactory2", queues = "queue.test.lib")
    public void onMessage(Message message) {
        process(message);
    }

    private void process(Message message) {
        String messageStr = new String(message.getBody(), Charset.defaultCharset());

        if ("dlq".equals(messageStr)) {
            throw new RuntimeException("to dead-letter");
        }

        log.info("message = [{}]", messageStr);
    }
}
