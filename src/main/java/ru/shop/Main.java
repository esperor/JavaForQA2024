package ru.shop;

import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.CustomerRepository;
import ru.shop.repository.ProductRepository;
import ru.shop.service.CustomerService;
import ru.shop.service.ProductService;
import ru.shop.repository.OrderRepository;
import ru.shop.service.OrderService;

import java.util.ArrayList;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        var productService = new ProductService(new ProductRepository());
        var customerService = new CustomerService(new CustomerRepository());
        var orderService = new OrderService(new OrderRepository());

        var products = new ArrayList<Product>();
        var customers = new ArrayList<Customer>();
        var orders = new ArrayList<Order>();

        products.add(productService.save(new Product(UUID.randomUUID(), "Laptop", 700, ProductType.GOOD)));
        products.add(productService.save(new Product(UUID.randomUUID(), "Smartphone", 200, ProductType.GOOD)));
        products.add(productService.save(new Product(UUID.randomUUID(), "Build a PC", 100, ProductType.SERVICE)));
        products.add(productService.save(new Product(UUID.randomUUID(), "TV", 400, ProductType.GOOD)));

        customers.add(customerService.save(new Customer(UUID.randomUUID(), "Alice", "123", 22)));
        customers.add(customerService.save(new Customer(UUID.randomUUID(), "Alex", "213", 25)));
        customers.add(customerService.save(new Customer(UUID.randomUUID(), "Robert", "123", 32)));
        customers.add(customerService.save(new Customer(UUID.randomUUID(), "Aleksandr", "123", 31)));

        orders.add(orderService.add(customers.get(0), products.get(1), 4));
        orders.add(orderService.add(customers.get(1), products.get(0), 1));
        orders.add(orderService.add(customers.get(2), products.get(3), 6));
        orders.add(orderService.add(customers.get(2), products.get(2), 1));
        orders.add(orderService.add(customers.get(3), products.get(2), 1));

        try {
            orders.add(orderService.add(customers.get(3), products.get(2), -2));
        }
        catch (BadOrderCountException e)
        {
            System.out.println("Exception handled");
        }

        System.out.printf("Number of customers: %s%n", customerService.findAll().size());
        System.out.printf("Number of orders overall: %s%n", orderService.findAll().size());
        for (ProductType value : ProductType.values()) {
            System.out.printf("Number of products of type %s: %s%n", value.name().trim(), productService.findByProductType(value).size());
        }

        System.out.printf("Number of order overall: %s%n", orderService.findAll().size());

        customerService.findAll().forEach(customer -> {
            System.out.printf("Number of orders by %s: %s%n", customer.getName().trim(), orderService.findByCustomer(customer).size());
        });

        customerService.findAll().forEach(customer -> {
            System.out.printf("Order amount for %s: %s%n", customer.getName().trim(), orderService.getTotalCustomerAmount(customer));
        });

    }

}