package yarosh.vlad.pizzaapp.repository;

import yarosh.vlad.pizzaapp.domain.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
