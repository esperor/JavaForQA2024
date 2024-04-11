package ru.shop.model;

import java.util.UUID;

public class Customer {
   private UUID id;
   private String name;
   private String phone;
   private long age;

    public Customer(UUID id, String name, String phone, long age) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public long getAge() {
        return age;
    }
}