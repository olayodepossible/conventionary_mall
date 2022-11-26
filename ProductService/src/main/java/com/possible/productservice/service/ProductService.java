package com.possible.productservice.service;

import com.possible.productservice.domain.OrderLine;
import com.possible.productservice.domain.Product;
import com.possible.productservice.domain.ProductDto;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String productNumber);

    Product addProduct(ProductDto product);

    void deleteProductById(String productNumber);

    Product editProduct(String productNumber, ProductDto product);

    Integer getProductNumInStock(String productNumber);

    Product addProductToStock(String productNumber, Integer quantity);

    Product removeProductFromStock(String productNumber, Integer quantity);

    void removeQuantityOfProducts(List<OrderLine> orderLines);


}
