package ru.shop.repository;

import ru.shop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements IRepository<Product> {
    List<Product> products = new ArrayList<>();

    public Product save(Product product)
    {
        products.add(product);
        return product;
    }

    public List<Product> findAll()
    {
        return products;
    }
}
