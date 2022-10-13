package com.possible.shoppingservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    private String name;
    private String desc;
    private Address vendorAddress;
}
