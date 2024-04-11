package ru.shop.service;

import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order add(Customer customer, Product product, long count)
    {
        if (count <= 0) throw new BadOrderCountException();
        return repository.save(new Order(UUID.randomUUID(), customer.getId(), product.getId(), count, product.getCost() * count));
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public List<Order> findByCustomer(Customer customer)
    {
        return repository.findAll().stream().filter(x -> x.getCustomerId().equals(customer.getId())).toList();
    }

    public long getTotalCustomerAmount(Customer customer)
    {
        return findByCustomer(customer).stream().map(Order::getAmount).reduce(Long::sum).orElse(0L);
    }
}
