package yarosh.vlad.pizzaapp.service.helper;

import yarosh.vlad.pizzaapp.domain.constant.OrderStatusEnum;
import yarosh.vlad.pizzaapp.domain.dto.binding.OrderBindingDto;
import yarosh.vlad.pizzaapp.domain.entity.Order;
import yarosh.vlad.pizzaapp.domain.entity.User;
import org.springframework.stereotype.Component;
import yarosh.vlad.pizzaapp.constant.ErrorMessages;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class OrderServiceHelper {

    public static void buildOrder(OrderBindingDto orderBindingDto, Order order, User user) {
        BigDecimal price = user
                .getCart()
                .getProductsSum()
                .add(BigDecimal.valueOf(user.getCart().getProductsCount() * 0.5))
                .add(BigDecimal.valueOf(5.00));

        price = orderBindingDto
                .getDiscount()
                .isEmpty() ? price : price.multiply(BigDecimal.valueOf(0.9));

        order
                .setOwner(user)
                .setPrice(price)
                .setDiscount(orderBindingDto.getDiscount())
                .setComment(orderBindingDto.getComment() != null ? orderBindingDto.getComment() : ErrorMessages.NO_COMMENT)
                .setAddress(orderBindingDto.getAddress())
                .setContactPhoneNumber(orderBindingDto.getContactPhoneNumber())
                .setCreatedOn(LocalDateTime.now())
                .setStatus(OrderStatusEnum.IN_PROGRESS);
    }
}
