package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadOrderCountException;
import ru.shop.model.Customer;
import ru.shop.model.Order;
import ru.shop.model.Product;
import ru.shop.model.ProductType;
import ru.shop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

class OrderServiceTest {

    private final OrderRepository repository = Mockito.mock();
    private final OrderService service = new OrderService(repository);

    @Test
    public void shouldAddOrder() {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "Nameee", "213124", 12);
        Product product = new Product(UUID.randomUUID(), "product", 120, ProductType.SERVICE);

        // when
        service.add(customer, product, 10);

        // then
        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.captor();

        verify(repository).save(orderArgumentCaptor.capture());
        Order savedOrder = orderArgumentCaptor.getValue();
        assertThat(savedOrder)
                .returns(10L, Order::getCount);
    }


    @ParameterizedTest
    @ValueSource(ints = { 0, -1, -2, Integer.MIN_VALUE })
    public void shouldThrowBadOrderCountExceptionWhenCountIsLessThanOrEqualsZero(Integer count) {
        // given
        Customer customer = new Customer(UUID.randomUUID(), "Nameee", "213124", 12);
        Product product = new Product(UUID.randomUUID(), "product", 120, ProductType.SERVICE);

        // when
        assertThrows(
                BadOrderCountException.class,
                () -> service.add(customer, product, count)
        );
    }

    @Test
    public void shouldFindByCustomer() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId, "name", "phone", 20
        );

        Order customerOrder = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 10, 10
        );
        Order notCustomerOrder = new Order(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 10
        );
        Mockito.when(repository.findAll())
                .thenReturn(
                        List.of(customerOrder, notCustomerOrder)
                );

        // when
        List<Order> lookUpResult = service.findByCustomer(customer);

        // then
        assertEquals(1, lookUpResult.size());
        assertEquals(customerOrder, lookUpResult.getFirst());
    }

    @Test
    public void shouldFindCustomerTotal() {
        // given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                customerId, "name", "phone", 20
        );

        Order customerOrder = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 10, 10
        );
        Order customerOrder2 = new Order(
                UUID.randomUUID(), customerId, UUID.randomUUID(), 10, 20
        );
        Order notCustomerOrder = new Order(
                UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 10
        );
        Mockito.when(repository.findAll())
                .thenReturn(
                        List.of(customerOrder, customerOrder2, notCustomerOrder)
                );

        // when
        long result = service.getTotalCustomerAmount(customer);

        // then
        assertEquals(30, result);
    }

}