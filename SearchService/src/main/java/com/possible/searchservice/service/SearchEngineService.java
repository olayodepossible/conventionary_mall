package com.possible.searchservice.service;

import com.possible.commonslib.avro.Product;
import com.possible.commonslib.avro.VendorEvent;

public interface SearchEngineService {
    Product createProduct(Product product);
    VendorEvent createVendor(VendorEvent vendor);
}
