package yarosh.vlad.pizzaapp.web.rest;

import yarosh.vlad.pizzaapp.domain.dto.view.OrderDetailsViewDto;
import yarosh.vlad.pizzaapp.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/order/details")
public class OrderDetailsController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailsViewDto> getOrderById(@PathVariable Long id) {
        OrderDetailsViewDto dto = orderService.getOrderById(id);
        return ResponseEntity.ok(dto);
    }
}
