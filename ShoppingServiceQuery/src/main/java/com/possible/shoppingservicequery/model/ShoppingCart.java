package com.possible.shoppingservicequery.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Document(value = "shoppingCartQuery")
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    @Id
    @Indexed
    private String customerId;
    private List<CartLine> cartLineList = new ArrayList<>();

}
