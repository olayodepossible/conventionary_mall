package com.possible.productservice.exception;


public class ProductNotfoundException extends RuntimeException {
    private String message;
    public ProductNotfoundException(String message) {
        super(message);
        this.message = message;
    }
    public ProductNotfoundException() {
    }
}