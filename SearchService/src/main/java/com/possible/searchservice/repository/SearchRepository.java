package com.possible.searchservice.repository;

import com.possible.vendorservice.domain.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchRepository extends MongoRepository<Vendor, String> {


}
