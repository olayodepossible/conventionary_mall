package com.possible.customerservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String productId;
    private String productLogo;
    private String productName;
    private Flavour productFlavour;
    private Double productPrice;
    private String vendorId;
    private String productDescription;
    private Integer productNumInStock;
    private int rating;

}