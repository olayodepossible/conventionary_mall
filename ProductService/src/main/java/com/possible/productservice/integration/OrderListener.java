package com.possible.productservice.integration;


import com.possible.commonslib.avro.OrderEvent;
import com.possible.productservice.domain.OrderLine;
import com.possible.productservice.domain.Product;
import com.possible.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Component
public class OrderListener {

    @Autowired
    private ProductService productService;
    private final String PARTITION_KEY = "productService";

    @Bean
    public Consumer<Message<OrderEvent>> orderEvent() {
        return message -> {
            log.info("PartitionKey **: {}", message.getHeaders());
            Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
            if (acknowledgment != null) {
                acknowledgment.acknowledge();
            }
            if (Objects.equals(message.getHeaders().get("partitionKey"), PARTITION_KEY)) {
                List<OrderLine> orderLines = new ArrayList<>();

                for (com.possible.commonslib.avro.OrderLine orderLine : message.getPayload().getOrderLineList()) {
                    Product product = Product.builder()
                            .productId(orderLine.getProduct().getProductId().toString())
                            .productName(orderLine.getProduct().getProductName().toString())
                            .productLogo(orderLine.getProduct().getProductLogo().toString())
                            .productPrice(orderLine.getProduct().getProductPrice())
                            .productNumInStock(orderLine.getProduct().getProductNumInStock())
                            .rating(orderLine.getProduct().getRating())
                            .build();
                    OrderLine orderLine1 = OrderLine.builder()
                            .product(product)
                            .quantity(orderLine.getQuantity())
                            .build();
                    orderLines.add(orderLine1);
                }
                productService.removeQuantityOfProducts(orderLines);
            }

        };
    }

}
