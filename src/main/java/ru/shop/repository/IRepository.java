package ru.shop.repository;

import java.util.List;

public interface IRepository<T> {
    T save(T item);
    List<T> findAll();
}
