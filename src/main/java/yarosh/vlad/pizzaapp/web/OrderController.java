package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.dto.binding.OrderBindingDto;
import yarosh.vlad.pizzaapp.service.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import static yarosh.vlad.pizzaapp.constant.ControllerConstants.*;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @ModelAttribute("orderDto")
    public OrderBindingDto initBindingDto() {
        return new OrderBindingDto();
    }

    @GetMapping("/finalize")
    public String getFinalizeOrder(Model model, Principal principal) {
        model.addAttribute(FOOD_PRICE, this.orderService.getProductsPrice(principal.getName()));
        model.addAttribute(PRODUCTS_COUNT, this.orderService.getProducts(principal.getName()).size());
        return "finalize-order";
    }

    @PostMapping("/finalize")
    public String postFinalizeOrder(
            @Valid OrderBindingDto orderDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderDto", orderDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderDto", bindingResult);
            return "redirect:/orders/finalize";
        }

        this.orderService.makeOrder(orderDto, principal.getName());
        return "redirect:/";
    }

    @GetMapping("/history")
    public String getOrdersHistory(Model model, Principal principal) {
        model.addAttribute(ORDERS, this.orderService.getOrdersByUser(principal.getName()));
        model.addAttribute(PRODUCTS_COUNT, this.orderService.getProducts(principal.getName()).size());
        return "orders-history-user";
    }

    @GetMapping("/details/{id}")
    public String getOrderDetails(@PathVariable Long id, Model model) {
        model.addAttribute(ORDER, this.orderService.getOrderById(id));
        model.addAttribute(ID_ATTRIBUTE, id);
        return "order-details-api";
    }

    @GetMapping("/all/history")
    public String getAllOrdersHistory(Model model) {
        model.addAttribute(ALL_ORDERS, this.orderService.getAllOrders());
        return "orders-history";
    }

    @PatchMapping("/finish/{id}")
    public String finishOrder(@PathVariable Long id) {
        this.orderService.finishOrder(id);
        return "redirect:/orders/all/history";
    }

    @PatchMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable Long id) {
        this.orderService.cancelOrder(id);
        return "redirect:/orders/all/history";
    }
}
