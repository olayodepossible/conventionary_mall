package com.possible.customerservice.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    private String street;
    private String city;
    private String zip;
    private String country;
    private int latitude;
    private int longitude;
}
