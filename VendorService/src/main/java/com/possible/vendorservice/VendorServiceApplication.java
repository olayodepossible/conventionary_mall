package com.possible.vendorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class VendorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendorServiceApplication.class, args);
    }

}
