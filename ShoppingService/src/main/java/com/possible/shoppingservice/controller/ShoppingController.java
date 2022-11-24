package com.possible.shoppingservice.controller;

import com.possible.shoppingservice.model.*;
import com.possible.shoppingservice.service.ShoppingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @Autowired
    private ShoppingFeignClient shoppingFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @PostMapping("/addCartForACustomer/{customerId}")
    public ResponseEntity<ShoppingCart> addShoppingCartForCustomer(@PathVariable String customerId){

        return new ResponseEntity<>(shoppingService.addShoppingCart(customerId),HttpStatus.OK);
    }

    @PostMapping("/addProductToCartWithQuantity/{customerId}/quantity/{quantity}")
    public ResponseEntity<ShoppingCart> addProductToShoppingCart(@PathVariable String customerId , @PathVariable Integer quantity
            , @RequestBody Product product){
        if(productFeignClient.checkProductInStock(product.getProductId(), quantity)){
            ShoppingCart shoppingCart = shoppingService.addProductToAShoppingCart(customerId, product, quantity);
            return new ResponseEntity<>(shoppingCart, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ShoppingCart(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/removeProductFromCartWithQuantity/{customerId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<?> removeProductWithQuantity(@PathVariable("customerId") String customerId ,
                                         @PathVariable("quantity") Integer quantity,
                                                       @PathVariable("productId") String productId){
        shoppingService.removeProductWithQuantity(customerId,productId,quantity);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/removeProductFromCart/{customerId}/product/{productId}")
    public ResponseEntity<?> removeAllProduct(@PathVariable("customerId") String customerId ,
                                              @PathVariable("productId") String productId){
        shoppingService.removeAllProduct(customerId,productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<Order> checkoutCart(@PathVariable String customerId){
        List<CartLine> cartLines =  shoppingService.checkoutCart(customerId);
        Order order = shoppingFeignClient.createOrder(cartLines, customerId);
        shoppingService.removeCartLine(customerId);
        return new ResponseEntity<>(order,HttpStatus.OK);

    }

    @FeignClient("OrderService")
    interface ShoppingFeignClient{
        @PostMapping("/order/{customerId}")
        Order createOrder(@RequestBody List<CartLine> cartLines, @PathVariable String customerId);

    }

    @FeignClient("ProductService")
    interface ProductFeignClient{
        @GetMapping("/products/{productId}/isInStock")
        boolean checkProductInStock(@PathVariable("productId") String productId, Integer quantity);

    }

}
