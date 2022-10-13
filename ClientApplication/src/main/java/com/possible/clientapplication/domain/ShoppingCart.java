package com.possible.clientapplication.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCart {
    private List<CartLine> cartLineList;

    @JsonCreator
    public ShoppingCart( List<CartLine> cartLineList) {
        this.cartLineList = cartLineList;
    }




}
