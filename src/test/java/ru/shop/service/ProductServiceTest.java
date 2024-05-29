package ru.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.shop.exception.EntityNotFoundException;
import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private final ProductRepository repository = Mockito.mock();
    private final ProductService productService = new ProductService(repository);

    @Test
    void shouldGetCustomer() {
        UUID productId = UUID.randomUUID();
        Product mockedProduct = new Product();
        Mockito
                .when(repository.findById(productId))
                .thenReturn(Optional.of(mockedProduct));

        Product product = productService.getById(productId);

        Assertions.assertEquals(mockedProduct, product);
    }

    @Test
    void shouldThrowNewProductNotFound()
    {
        Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> productService.getById(UUID.randomUUID())
        );
    }
}