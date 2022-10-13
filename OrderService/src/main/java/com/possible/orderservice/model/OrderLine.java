package com.possible.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderLine {

    private Product product;
    private Integer quantity;
}
