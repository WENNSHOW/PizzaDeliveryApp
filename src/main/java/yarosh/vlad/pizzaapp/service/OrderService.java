package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.constant.OrderStatusEnum;
import yarosh.vlad.pizzaapp.domain.dto.binding.OrderBindingDto;
import yarosh.vlad.pizzaapp.domain.dto.view.OrderDetailsViewDto;
import yarosh.vlad.pizzaapp.domain.dto.view.ProductViewDto;
import yarosh.vlad.pizzaapp.domain.entity.Order;
import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.exception.ObjectNotFoundException;
import yarosh.vlad.pizzaapp.repository.OrderRepository;
import yarosh.vlad.pizzaapp.service.helper.OrderServiceHelper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yarosh.vlad.pizzaapp.constant.ControllerConstants;
import yarosh.vlad.pizzaapp.constant.ErrorMessages;
import yarosh.vlad.pizzaapp.constant.Messages;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public List<ProductViewDto> getProducts(String email) {
        User user = this.userService.getUserByEmail(email);
        return user
                .getCart()
                .getProducts()
                .stream()
                .map(product -> this.modelMapper.map(product, ProductViewDto.class))
                .collect(Collectors.toList());
    }

    public BigDecimal getProductsPrice(String email) {
        User user = this.userService.getUserByEmail(email);
        return user.getCart().getProductsSum();
    }

    @Transactional
    public void makeOrder(OrderBindingDto orderBindingDto, String email) {
        Order order = new Order();
        User user = this.userService.getUserByEmail(email);

        OrderServiceHelper.buildOrder(orderBindingDto, order, user);

        this.orderRepository.saveAndFlush(order);
        user.getCart()
                .setProducts(new ArrayList<>())
                .setProductsSum(BigDecimal.ZERO)
                .setProductsCount(0L);
    }

    @Transactional
    public List<OrderDetailsViewDto> getOrdersByUser(String email) {
        User user = this.userService.getUserByEmail(email);
        return this.orderRepository
                .findAllOrdersByOwnerId(user.getId())
                .stream()
                .map(this::mapToOrderViewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDetailsViewDto> getInProgressOrdersByUser(User user) {
        return this.orderRepository
                .findAllOrdersByOwnerIdAndStatus(user.getId(), OrderStatusEnum.IN_PROGRESS)
                .stream()
                .map(this::mapToOrderViewDto)
                .collect(Collectors.toList());
    }

    public OrderDetailsViewDto getOrderById(Long id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Messages.ORDER, ControllerConstants.ID, id));

        if (order.getComment().isEmpty()) {
            order.setComment(ErrorMessages.NO_COMMENT);
        }

        return mapToOrderViewDto(order);
    }

    public List<OrderDetailsViewDto> getAllOrders() {
        return this.orderRepository
                .findAll()
                .stream()
                .map(this::mapToOrderViewDto)
                .collect(Collectors.toList());
    }

    public void finishOrder(Long id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Messages.ORDER, ControllerConstants.ID, id));
        order.setStatus(OrderStatusEnum.DELIVERED);
        order.setDeliveredOn(LocalDateTime.now());
        this.orderRepository.saveAndFlush(order);
    }

    public void cancelOrder(Long id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(Messages.ORDER, ControllerConstants.ID, id));
        order.setStatus(OrderStatusEnum.CANCELLED);
        this.orderRepository.saveAndFlush(order);
    }

    private OrderDetailsViewDto mapToOrderViewDto(Order order) {
        OrderDetailsViewDto orderDetailsViewDto = this.modelMapper.map(order, OrderDetailsViewDto.class);
        orderDetailsViewDto.setClient(order.getOwner().getUsername());
        return orderDetailsViewDto;
    }
}
