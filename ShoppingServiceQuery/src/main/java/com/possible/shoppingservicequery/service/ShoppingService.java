package com.possible.shoppingservicequery.service;

import com.possible.shoppingservicequery.model.CartLine;
import com.possible.shoppingservicequery.model.Product;
import com.possible.shoppingservicequery.model.ShoppingCart;
import com.possible.shoppingservicequery.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    public void addShoppingCart(ShoppingCart shoppingCart){
        shoppingRepository.save(shoppingCart);
    }

    public void addProductToAShoppingCart(String customerId, Product product, Integer quantity){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        List<CartLine> cartLineList = shoppingCart.getCartLineList();

        for(CartLine cartLine  : cartLineList){
            if(cartLine.getProduct().equals(product)){
                cartLine.changeQuantity(cartLine.getQuantity() + quantity);
                return;
            }
        }
        cartLineList.add(new CartLine(product,quantity));
        shoppingRepository.save(shoppingCart);
    }

    public boolean removeProductWithQuantity(String customerId , Product product , Integer quantity){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        List<CartLine> cartLineList = shoppingCart.getCartLineList();

        CartLine cartLine2= null;
        for(CartLine cartLine : cartLineList){
            if(cartLine.getProduct().equals(product) && cartLine.getQuantity() > quantity ){
                cartLine.changeQuantity(cartLine.getQuantity() - quantity);
                return true;
            }
            else if(cartLine.getProduct().equals(product) && cartLine.getQuantity() <= quantity ){
                cartLineList.remove(cartLine);
                return false;
            }
            else{
                cartLine2=cartLine;
            }
        }
        cartLineList.remove(cartLine2);
        shoppingRepository.save(shoppingCart);
        return true;
    }

    public void removeAllProduct(String customerId , Product product ){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        List<CartLine> cartLineList = shoppingCart.getCartLineList();

        for(CartLine cartLine2 : cartLineList){
            if(cartLine2.getProduct().equals(product)){
                cartLineList.remove(cartLine2);
                return;
            }
        }

        shoppingRepository.save(shoppingCart);
    }

    public List<CartLine> viewShoppingCart(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElse(new ShoppingCart());
        return shoppingCart.getCartLineList();

    }

    public void removeCartLine(String customerId){
        ShoppingCart shoppingCart = shoppingRepository.findByCustomerId(customerId).orElseThrow();
        shoppingCart.setCartLineList(new ArrayList<>());
        shoppingRepository.save(shoppingCart);
    }

}
