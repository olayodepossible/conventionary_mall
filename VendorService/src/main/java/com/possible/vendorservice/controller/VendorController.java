package com.possible.vendorservice.controller;


import com.possible.vendorservice.domain.Vendor;
import com.possible.vendorservice.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/save")
    public Vendor saveVendor(@RequestBody Vendor Vendor){
        return vendorService.saveVendor(Vendor);
    }

    @PostMapping("/update")
    public ResponseEntity<Vendor> updateVendor(@RequestBody Vendor Vendor){
        return ResponseEntity.ok(vendorService.updateVendor(Vendor));

    }
    @GetMapping("/delete{VendorId}")
    public  void deleteVendor(@RequestParam String VendorId){

        vendorService.deleteVendor(VendorId);

    }
    @GetMapping("/find/{VendorId}")
    public ResponseEntity<Vendor> findById(@RequestParam  String VendorId)
    {
        return ResponseEntity.ok(vendorService.findById(VendorId).orElse(null));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Vendor>> findAll(){
        return ResponseEntity.ok(new ArrayList<>(vendorService.findAll()));
    }
}
