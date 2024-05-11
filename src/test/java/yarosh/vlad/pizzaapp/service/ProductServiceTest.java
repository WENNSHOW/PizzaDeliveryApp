package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.exception.ObjectNotFoundException;
import yarosh.vlad.pizzaapp.exception.WrongCategoryException;
import yarosh.vlad.pizzaapp.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private ProductService productService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, modelMapper);
    }


    @Test
    void testWrongCategory_NotSuccessful() {
        Assertions.assertThrows(WrongCategoryException.class,
                () -> this.productService.findCategory("category"));
    }

    @Test
    void testGetProductById_NotSuccessful() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> this.productService.getProductById(-1L));
    }

}
