package com.possible.shoppingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
