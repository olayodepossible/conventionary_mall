package com.possible.productservice.controller;


import com.possible.productservice.domain.Product;
import com.possible.productservice.domain.ProductDto;
import com.possible.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        log.info("Calling get all Products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") String productId){
        log.info("Calling get product by id - {}", productId);
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto product){
        log.info("Calling add product" );
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @DeleteMapping("/{productNumber}")
    public void deleteProductById(@PathVariable("productNumber") String productNumber){
        log.info("Calling delete product by id - {}", productNumber);
        productService.deleteProductById(productNumber);
    }

    @PutMapping("/{productNumber}")
    public ResponseEntity<Product> editProduct(@PathVariable String productNumber,
                                               @RequestBody ProductDto product){
        log.info("Calling edit product by id - {}", productNumber);
        return ResponseEntity.ok(productService.editProduct(productNumber, product));
    }

    @PostMapping("/{productId}/isInStock")
    public boolean isProductInStock(@PathVariable String productId, @RequestParam Integer quantity){
        return productService.getProductNumInStock(productId) > quantity;
    }

}
