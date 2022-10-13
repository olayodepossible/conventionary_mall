package com.possible.orderservice.service;

import com.possible.orderservice.integration.Message;
import com.possible.orderservice.integration.OrderStreamSender;
import com.possible.orderservice.model.*;
import com.possible.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStreamSender orderStreamSender;


    public List<Order> getOrders(){
            return new ArrayList<>(orderRepository.findAll());
    }

    public Order createOrder(List<CartLine> cartLines, String customerId){

        Order order = new Order();
        List<OrderLine> orderLineList = new ArrayList<>();
        for(CartLine cartLine : cartLines){
            OrderLine orderLine = OrderLine.builder()
                    .product(cartLine.getProduct())
                    .quantity(cartLine.getQuantity())
                    .build();
            orderLineList.add(orderLine);
        }

        order.setOrderLineList(orderLineList);
        order.setCustomerId(customerId);
        return orderRepository.save(order);
    }

    public OrderResponse placeOrder(String orderId){
        Order order = orderRepository.findByOrderId(orderId).orElseThrow(()-> new RuntimeException("Order not found"));
//        orderRepository.save(order); TODO

        Message<List<OrderLine>> messageOrderLines = new Message<>("productService" , order.getOrderLineList());
        orderStreamSender.sendOrder(order, "productService", "orderEvent");
        //productService gets Updated
//        orderStreamSender.send(messageOrderLines);

        Message<Order> messageOrder = new Message<>("customerService",order);
        log.info("This is the message to be send as an Email to customer {}\n", messageOrder);
        orderStreamSender.sendOrder(order, "customerService", "customerOrderEvent");
//        orderStreamSender.send(messageOrder);
        return OrderResponse.builder().isSuccess(true).orderCount(order.getOrderLineList().size()).message("Order placed successfully").build();
    }
}
