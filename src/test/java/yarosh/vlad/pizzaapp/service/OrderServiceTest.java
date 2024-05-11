package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.exception.ObjectNotFoundException;
import yarosh.vlad.pizzaapp.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ModelMapper modelMapper;

    private OrderService orderService;


    @BeforeEach
    void setUp() {
        orderService = new OrderService(userService, orderRepository, modelMapper);
    }

    @Test
    void testFindOrderById_NotSuccessful() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> this.orderService.getOrderById(-1L));
    }

    @Test
    void testFinishOrderById_NotSuccessful() {
        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> this.orderService.finishOrder(-1L));
    }
}
