package com.possible.shoppingservicequery.repository;

import com.possible.shoppingservicequery.model.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingRepository extends MongoRepository<ShoppingCart,String> {

    public Optional<ShoppingCart> findByCustomerId(String customerId);
}
