package com.possible.orderservice.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Customer {

    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Address address;
    private Gender status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
