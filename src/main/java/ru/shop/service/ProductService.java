package ru.shop.service;

import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.ProductRepository;

import java.util.List;

public class ProductService implements IService<Product> {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product p) {
        return repository.save(p);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public List<Product> findByProductType(ProductType type) {
        return repository.findAll().stream().filter(x -> x.getProductType().equals(type)).toList();
    }

}
