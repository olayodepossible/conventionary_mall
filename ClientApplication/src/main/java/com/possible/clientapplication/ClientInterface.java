package com.possible.clientapplication;

import com.possible.clientapplication.domain.Product;

import java.util.List;

public interface ClientInterface {

    public Product addProduct(Product product);
//    public void addProduct(Product product);
    public Product modifyProduct(Product product ,String productId);

    public List<Product> getProducts();
    public void addProductToShoppingCart(String customerId,Product product);
    public void showShoppingCart(String customerId);
    public void removeProductFromShoppingCart(String customerId,String product);
    public void checkoutShoppingCart(String customerId);
    public void placeAnOrder(String orderNumber);


}
