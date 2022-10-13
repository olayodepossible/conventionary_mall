package com.possible.clientapplication;


import com.possible.clientapplication.domain.Product;
import com.possible.clientapplication.domain.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class Client implements ClientInterface {

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private RestTemplate restTemplate;

    private final String baseProductUrl = "http://localhost:8080/products/";

    private final String addProductToShoppingCartUrl = "http://localhost:8080/cart/{customerId}"; // shoppingService
    private final String showShoppingCartUrl = "http://localhost:8080/cartQuery/getShoppingCart/{customerId}"; //ShoppingServiceQuery
    private final String removeProductFromShoppingCartUrl = "http://localhost:8080/cart";
    private final String checkoutShoppingCartUrl = "http://localhost:8080/cart/checkout/{customerId}";
    private final String placeAnOrderUrl = "http://localhost:8080/order/placeOrder/orderNumber/{orderNumber}"; // OrderService


    @Override
    public Product addProduct(Product product) {
        log.info("PRODUCT FROM CLIENT ************* {}", product);
        return restTemplate.postForObject(baseProductUrl, product, Product.class);
//        return restTemplate.postForLocation(baseProductUrl, product, Product.class);
    }

    @Override
    public Product modifyProduct(Product product, String productId) {
        restTemplate.put(baseProductUrl + "/" + productId, product, Product.class);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        Product[] productArray = restTemplate.getForObject(baseProductUrl, Product[].class);
        return Arrays.asList(Objects.requireNonNull(productArray));
    }

    @Override
    public void addProductToShoppingCart(String customerId, Product product) {
        URI uri = restTemplate.postForLocation(addProductToShoppingCartUrl, product, customerId);
        log.info("addProductToShoppingCart url: {}", uri);

    }

    @Override
    public void showShoppingCart(String customerId) {
        restTemplate.getForObject(showShoppingCartUrl, ShoppingCart.class, customerId);
    }

    @Override
    public void removeProductFromShoppingCart(String customerId, String productId) {
        restTemplate.delete(removeProductFromShoppingCartUrl, customerId, productId);
    }

    @Override
    public void checkoutShoppingCart(String customerId) {
        URI uri = restTemplate.postForLocation(checkoutShoppingCartUrl, customerId);
        System.out.println(uri);

    }

    @Override
    public void placeAnOrder(String orderNumber) {
        URI uri = restTemplate.postForLocation(placeAnOrderUrl, orderNumber);
        System.out.println(uri);


    }

}
