package com.possible.shoppingservice.integration;

import com.possible.shoppingservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerProductQuantityDTO {


    private String customerId;

    private Product product;

    private Integer quantity;


}
