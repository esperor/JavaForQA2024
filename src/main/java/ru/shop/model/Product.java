package ru.shop.model;

import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private long cost;
    private ProductType productType;

    public Product(UUID id, String name, long cost, ProductType productType) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.productType = productType;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getCost() {
        return cost;
    }

    public ProductType getProductType() {
        return productType;
    }
}