package ru.shop.model;

import java.util.UUID;

public class Order {
    private UUID id;
    private UUID customerId;
    private UUID productId;
    private long count;
    private long amount;

    public Order(UUID id, UUID customerId, UUID productId, long count, long amount) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
        this.count = count;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getCount() {
        return count;
    }

    public long getAmount() {
        return amount;
    }
}
