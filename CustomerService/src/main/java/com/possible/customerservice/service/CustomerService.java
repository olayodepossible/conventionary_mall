package com.possible.customerservice.service;

import com.possible.customerservice.domain.Customer;
import com.possible.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);

    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    public Optional<Customer> findById(String customerId) {
        return customerRepository.findById(customerId);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
