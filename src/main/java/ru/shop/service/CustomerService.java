package ru.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Customer;
import ru.shop.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService  implements IService<Customer> {

    private final CustomerRepository repository;

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public List<Customer> findAll() {
        return repository.findAll();
    }

    public Customer getById(UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
