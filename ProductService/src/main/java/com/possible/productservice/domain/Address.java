package com.possible.productservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String street;
    private String city;
    private String zip;
    private String country;
    private int latitude;
    private int longitude;
}
