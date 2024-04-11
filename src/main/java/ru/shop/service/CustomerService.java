package ru.shop.service;

import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.List;

public class CustomerService implements IService<Customer> {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer save(Customer p) {
        return repository.save(p);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }
}
