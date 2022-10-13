package com.possible.shoppingservice.service;

import com.possible.shoppingservice.integration.*;
import com.possible.shoppingservice.model.CartLine;
import com.possible.shoppingservice.model.Product;
import com.possible.shoppingservice.model.ShoppingCart;
import com.possible.shoppingservice.repository.ShoppingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ShoppingEvent sender;


    public ShoppingCart addShoppingCart(String customerId){
        Message<String> message = MessageBuilder.withPayload(customerId)
                .setHeader("partitionKey", "addCart")
                .build();
        ShoppingCart cart = new ShoppingCart();
        cart.setCustomerId(customerId);
       ShoppingCart shoppingCart = shoppingRepository.save(cart);

        sender.sendEvent(message);
        return shoppingCart;
    }

    public ShoppingCart addProductToAShoppingCart(String customerId, Product product, Integer quantity){
        Message<CustomerProductQuantityDTO> customerProductQualityDTOMessage = MessageBuilder.withPayload(new CustomerProductQuantityDTO(
                        customerId, product, quantity))
                .setHeader("partitionKey", "addProductAndQuantity")
                .build();

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        List<CartLine> cartLineList = shoppingCart.getCartLineList();

        if (!cartLineList.isEmpty()){
            for(CartLine cartLine  : cartLineList){
                if(cartLine.getProduct().equals(product)){
                    cartLine.changeQuantity(cartLine.getQuantity() + quantity);
                }
            }
        }
        cartLineList.add(new CartLine(product,quantity));

        log.info("*************shoppingCart IN SHOPINGSERVICE*******{}", shoppingCart);
        shoppingRepository.save(shoppingCart);

        sender.sendEvent(customerProductQualityDTOMessage);
        return shoppingCart;

    }

    public boolean removeProductWithQuantity(String customerId , String productId , Integer quantity){

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        List<CartLine> cartLineList = shoppingCart.getCartLineList();
        Product product = null;
        for(CartLine cartLine : cartLineList){

            if(cartLine.getProduct().getProductId().equals(productId)){
                if( cartLine.getQuantity() > quantity ){
                    cartLine.changeQuantity(cartLine.getQuantity() - quantity);
                    product = cartLine.getProduct();
                    return true;
                }
                else if(cartLine.getQuantity() <= quantity ){
                    cartLineList.remove(cartLine);
                    return false;
                }

            cartLineList.remove(cartLine);
            }
        }
        Message<CustomerProductQuantityDTO> customerProductQualityDTOMessage = MessageBuilder.withPayload(new CustomerProductQuantityDTO(customerId,product,quantity))
                .setHeader("partitionKey", "removeProductWithQuality")
                .build();

        shoppingRepository.save(shoppingCart);
        sender.sendEvent(customerProductQualityDTOMessage);
        return true;
    }

    public void removeAllProduct(String customerId , String productId ){

        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();

        for(CartLine cartLine : shoppingCart.getCartLineList()){

            if(cartLine.getProduct().getProductId().equals(productId)){

                shoppingCart.getCartLineList().remove(cartLine);
               log.info("Shopping cart in SHOPPING SERVICE{}", shoppingCart);

                Message<CustomerProductDTO> customerProductQualityDTOMessage = MessageBuilder.withPayload(new CustomerProductDTO( customerId,cartLine.getProduct()))
                        .setHeader("partitionKey", "removeAllProduct")
                        .build();
                shoppingRepository.save(shoppingCart);
                sender.sendEvent(customerProductQualityDTOMessage);
                return;

            }

        }
    }

    public List<CartLine> checkoutCart(String customerId){

        Message<String> message = MessageBuilder.withPayload(customerId)
                .setHeader("partitionKey", "checkout").build();

        ShoppingCart cart = shoppingRepository.findByCustomerId(customerId).orElseThrow();

        sender.sendEvent(message);
        log.info("This is the list of CartLines : {}", cart.getCartLineList());

        return cart.getCartLineList();
    }

    public void removeCartLine(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        shoppingCart.setCartLineList(new ArrayList<>());
        shoppingRepository.save(shoppingCart);
    }


}
