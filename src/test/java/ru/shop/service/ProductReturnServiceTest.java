package ru.shop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.shop.exception.BadProductReturnCountException;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Order;
import ru.shop.model.ProductReturn;
import ru.shop.repository.ProductRepository;
import ru.shop.repository.ProductReturnRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class ProductReturnServiceTest {

    private final ProductReturnRepository repository = Mockito.mock();
    private final ProductReturnService service = new ProductReturnService(repository);

    @Test
    void shouldSaveProductReturn() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 10, 2000);

        service.save(order, 7);

        ArgumentCaptor<ProductReturn> argumentCaptor = ArgumentCaptor.captor();

        verify(repository).save(argumentCaptor.capture());
        ProductReturn savedProductReturn = argumentCaptor.getValue();
        assertThat(savedProductReturn)
                .returns(7L, ProductReturn::getQuantity)
                .returns(order.getId(), ProductReturn::getOrderId);
    }

    @Test
    void shouldThrowBadProductReturnCountExceptionWhenQuantityMoreThanInOrder() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 8, 2000);

        assertDoesNotThrow(
                () -> service.save(order, 8)
        );

        assertThrows(
                BadProductReturnCountException.class,
                () -> service.save(order, 9)
        );
    }

    @Test
    void shouldFindAll() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 4, 4000);
        ProductReturn productReturn1 = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), 3);
        ProductReturn productReturn2 = new ProductReturn(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 6);
        ProductReturn productReturn3 = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), 2);

        var list = List.of(productReturn1, productReturn2, productReturn3);

        Mockito.when(repository.findAll())
                .thenReturn(list);

        List<ProductReturn> found = service.findAll();
        assertEquals(list, found);
    }

    @Test
    void shouldFindById() {
        UUID seekId = UUID.randomUUID();
        ProductReturn productReturn1 = new ProductReturn(seekId, UUID.randomUUID(), LocalDate.now(), 3);

        Mockito.when(repository.findById(seekId))
                .thenReturn(Optional.of(productReturn1));

        ProductReturn found = service.findById(seekId);
        assertEquals(productReturn1, found);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenWrongId() {
        Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), 4, 4000);
        ProductReturn productReturn1 = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), 3);
        ProductReturn productReturn2 = new ProductReturn(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), 6);
        ProductReturn productReturn3 = new ProductReturn(UUID.randomUUID(), order.getId(), LocalDate.now(), 2);

        var list = List.of(productReturn1, productReturn2, productReturn3);

        Mockito.when(repository.findAll())
                .thenReturn(list);

        assertThrows(
                EntityNotFoundException.class,
                () -> service.findById(UUID.randomUUID())
        );
    }
}