package com.possible.orderservice.controller;

import com.possible.orderservice.model.CartLine;
import com.possible.orderservice.model.Order;
import com.possible.orderservice.model.OrderResponse;
import com.possible.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/orderNumber/{orderNumber}")
    public ResponseEntity<OrderResponse> placeOrder(@PathVariable String orderNumber){
        return new ResponseEntity<>(orderService.placeOrder(orderNumber), HttpStatus.OK);
    }

    @GetMapping("/getOrders")
    public ResponseEntity<List<Order>> getOrders(){
        return new ResponseEntity<>(orderService.getOrders(),HttpStatus.OK);
    }


    @PostMapping("/{customerId}")
    public ResponseEntity<Order> createOrder(@RequestBody List<CartLine> cartLines, @PathVariable String customerId){
        Order order = orderService.createOrder(cartLines, customerId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
