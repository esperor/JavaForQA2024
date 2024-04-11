package ru.shop.repository;

import ru.shop.model.Customer;
import ru.shop.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements IRepository<Customer> {
    List<Customer> products = new ArrayList<>();

    public Customer save(Customer customer)
    {
        products.add(customer);
        return customer;
    }

    public List<Customer> findAll()
    {
        return products;
    }
}