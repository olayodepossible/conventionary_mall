package com.possible.shoppingservice.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;


@Slf4j
@Component
@RequiredArgsConstructor
public class ShoppingEvent {

    private final StreamBridge streamBridge;

    public void sendEvent(Message<?> message) {
        streamBridge.send("shoppingServiceTopic", message);
    }
}

