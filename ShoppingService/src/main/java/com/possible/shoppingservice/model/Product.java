package com.possible.shoppingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
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