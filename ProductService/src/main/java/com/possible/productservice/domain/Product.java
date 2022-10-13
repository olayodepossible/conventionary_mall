package com.possible.productservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Product {
    @Id
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
