package com.possible.shoppingservicequery.controller;

import com.possible.shoppingservicequery.model.CartLine;
import com.possible.shoppingservicequery.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartQuery")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @GetMapping("/getShoppingCart/{customerId}")
    public ResponseEntity<List<CartLine>> getShoppingCart(@PathVariable String customerId){

        return new ResponseEntity<>(shoppingService.viewShoppingCart(customerId),HttpStatus.OK);
    }



}
