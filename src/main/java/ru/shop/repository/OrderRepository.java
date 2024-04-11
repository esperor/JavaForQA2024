package ru.shop.repository;

import ru.shop.model.Order;
import ru.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IRepository<Order> {
    List<Order> products = new ArrayList<>();

    public Order save(Order order)
    {
        products.add(order);
        return order;
    }

    public List<Order> findAll()
    {
        return products;
    }
}
