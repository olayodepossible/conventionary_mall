package com.possible.customerservice.integration;

import com.possible.commonslib.avro.OrderEvent;
import com.possible.commonslib.avro.OrderLine;
import com.possible.customerservice.domain.Customer;
import com.possible.customerservice.domain.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.possible.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
@Service
public class Listener {

    private static final String PARTITION_KEY = "customerService";
    @Autowired
    private CustomerService customerService;

    @Bean
    public Consumer<Message<OrderEvent>> customerOrderEvent() {
        return message -> {
            log.info("PartitionKey ->: {}", message.getHeaders());
            if (Objects.equals(message.getHeaders().get("partitionKey"),PARTITION_KEY)){
                OrderEvent orderEvent = message.getPayload();
                List<OrderLine> orderLines =  orderEvent.getOrderLineList();
                String customerId = orderEvent.getCustomerId().toString();
                Customer customer = customerService.findById(customerId).orElseThrow();
                log.info("Order placed for this customer ={} is {}", customer, orderLines ); // TODO - send to Notification Service
            }

        };
    }

}
