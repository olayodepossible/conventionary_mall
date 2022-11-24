package com.possible.searchservice.service;

import com.possible.commonslib.avro.Product;
import com.possible.commonslib.avro.VendorEvent;
import com.possible.searchservice.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchEngineServiceImpl implements SearchEngineService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public VendorEvent createVendor(VendorEvent vendor) {
        return null;
    }
}
