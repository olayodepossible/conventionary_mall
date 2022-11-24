package com.possible.searchservice.controller;


import com.possible.searchservice.service.SearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vendor")
public class SearchServiceController {

    @Autowired
    private SearchEngineService searchServices;


}
