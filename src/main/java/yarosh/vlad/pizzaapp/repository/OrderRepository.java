package yarosh.vlad.pizzaapp.repository;

import yarosh.vlad.pizzaapp.domain.constant.OrderStatusEnum;
import yarosh.vlad.pizzaapp.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllOrdersByOwnerId(Long id);

    List<Order> findAllOrdersByOwnerIdAndStatus(Long id, OrderStatusEnum orderStatusEnum);
}
