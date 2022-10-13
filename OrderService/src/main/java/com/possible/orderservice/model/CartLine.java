package com.possible.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartLine {

    private Product product;

    private Integer quantity;

    private void changeQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
