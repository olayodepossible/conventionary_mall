package com.possible.orderservice.integration;

import com.possible.commonslib.avro.OrderEvent;
import com.possible.commonslib.avro.OrderLine;
import com.possible.commonslib.avro.Product;
import com.possible.orderservice.model.Order;
import com.possible.orderservice.model.OrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStreamSender {

    private final StreamBridge streamBridge;

    @Value("${spring.cloud.stream.bindings.sendOrder-out-0.content-type}")
    private String orderOutMimeType;

    public void sendOrder(Order order, String partitionKey, String binderName) {
        List<OrderLine> orderLineList = new ArrayList<>();
        for (com.possible.orderservice.model.OrderLine orderLine : order.getOrderLineList()){
            Product productAvro = Product.newBuilder()
                    .setProductId(orderLine.getProduct().getProductId())
                    .setProductName(orderLine.getProduct().getProductName())
                    .setProductLogo(orderLine.getProduct().getProductLogo())
                    .setProductFlavour(orderLine.getProduct().getProductFlavour().name())
                    .setProductDescription(orderLine.getProduct().getProductDescription())
                    .setProductPrice(orderLine.getProduct().getProductPrice())
                    .setProductNumInStock(orderLine.getProduct().getProductNumInStock())
                    .setVendorId(orderLine.getProduct().getVendorId())
                    .setRating(orderLine.getProduct().getRating())
                    .build();
            OrderLine orderLineAvro = OrderLine.newBuilder()
                    .setProduct(productAvro)
                    .setQuantity(orderLine.getQuantity())
                    .build();
            orderLineList.add(orderLineAvro);
        }
        OrderEvent orderEvent = OrderEvent.newBuilder()
                .setOrderId(order.getOrderId())
                .setCustomerId(order.getCustomerId())
                .setOrderLineList(orderLineList)
                .build();
        Message<OrderEvent> message = MessageBuilder.withPayload(orderEvent)
                .setHeader("partitionKey", partitionKey)
                .build();
        streamBridge.send(binderName, message, MimeType.valueOf(orderOutMimeType));

        log.info("Order with id '{}' and customerId '{}' sent to bus name {}.", message.getPayload().getOrderId(), message.getPayload().getCustomerId(), binderName);
    }


//    @Bean
//    public Supplier<OrderDto>sendOrder(Order order, String partitionKey){
//        OrderDto dto = OrderDto.builder()
//                .orderId(order.getOrderId())
//                .customerId(order.getCustomerId())
//                .orderLineList(order.getOrderLineList())
//                .build();
//        Message<OrderDto> message = MessageBuilder.withPayload(dto)
//                .setHeader("partitionKey", partitionKey)
//                .build();
//        return (Supplier<OrderDto>) message;
//    }
}
