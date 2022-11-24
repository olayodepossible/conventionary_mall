package com.possible.vendorservice.service;

import com.possible.vendorservice.domain.Product;
import com.possible.vendorservice.domain.Vendor;
import com.possible.vendorservice.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor saveVendor(Vendor Vendor) {

        return vendorRepository.save(Vendor);

    }

    public Vendor updateVendor(Vendor Vendor) {
        return vendorRepository.save(Vendor);
    }

    public void deleteVendor(String VendorId) {
        vendorRepository.deleteById(VendorId);
    }

    public Optional<Vendor> findById(String VendorId) {
        return vendorRepository.findById(VendorId);
    }

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }
}
