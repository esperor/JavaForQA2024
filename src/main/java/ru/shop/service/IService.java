package ru.shop.service;

import java.util.List;

public interface IService<T> {
    T save(T item);
    List<T> findAll();

}
