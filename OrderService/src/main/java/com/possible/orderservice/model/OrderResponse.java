package com.possible.orderservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private boolean isSuccess;
    private String message;
    private int orderCount;
}
