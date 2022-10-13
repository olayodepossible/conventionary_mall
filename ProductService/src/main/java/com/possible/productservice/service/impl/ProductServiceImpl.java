package com.possible.productservice.service.impl;

import com.possible.productservice.domain.OrderLine;
import com.possible.productservice.exception.ProductNotfoundException;
import com.possible.productservice.domain.Product;
import com.possible.productservice.repository.ProductRepository;
import com.possible.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final String PRODUCT_NOT_FOUND = "Product not found";

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String productNumber) {
        return productRepository.findById(productNumber).orElse(null);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProductById(String productNumber) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if (product==null) throw new ProductNotfoundException(PRODUCT_NOT_FOUND);
        productRepository.deleteById(productNumber);
    }

    @Override
    public Product editProduct(String productNumber, Product product) {
        Product oldProduct= productRepository.findById(productNumber).orElse(null);
        if (oldProduct == null) throw new ProductNotfoundException(PRODUCT_NOT_FOUND);
        oldProduct.setProductName(product.getProductName());
        oldProduct.setProductPrice(product.getProductPrice());
        oldProduct.setProductDescription(product.getProductDescription());
        oldProduct.setProductNumInStock(product.getProductNumInStock());
        return productRepository.save(oldProduct);

    }

    @Override
    public Integer getProductNumInStock(String productId) {
        Product product= productRepository.findById(productId).orElse(null);
        if(product==null) return 0;
        return product.getProductNumInStock();
    }

    @Override
    public Product addProductToStock(String productNumber, Integer quantity) {
        Product product= productRepository.findById(productNumber).orElse(null);
        if(product==null) throw new ProductNotfoundException(PRODUCT_NOT_FOUND); //handle exception
        product.setProductNumInStock(product.getProductNumInStock()+quantity);
        return productRepository.save(product);
    }

    @Override
    public Product removeProductFromStock(String productId, Integer quantity) {
        log.info("This is to update product with ID {} and quantity is => {}", productId, quantity);
        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) return null; //handle exception


        Integer productNumInStock= product.getProductNumInStock();

        product.setProductNumInStock(productNumInStock - quantity);
        //if product number in stock is 0, delete the product
        if(product.getProductNumInStock() == 0) {
            productRepository.delete(product);
            return product;
        }
        return productRepository.save(product);
    }

    @Override
    public void removeQuantityOfProducts(List<OrderLine> orderLines) {
        if (orderLines == null) return;
        orderLines.forEach(orderLine->{
            removeProductFromStock(orderLine.getProduct().getProductId(), orderLine.getQuantity());
        });
    }
}
