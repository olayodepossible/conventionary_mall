package com.possible.shoppingservicequery.integration;

import com.possible.shoppingservicequery.model.Product;
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
