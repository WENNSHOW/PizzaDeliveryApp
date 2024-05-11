package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.service.OrderService;
import yarosh.vlad.pizzaapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

import static yarosh.vlad.pizzaapp.constant.ControllerConstants.*;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/")
    public String getHome(Principal principal, Model model) {
        if (principal != null) {
            User user = this.userService.getUserByEmail(principal.getName());
            model.addAttribute(EMAIL, user.getEmail());
            model.addAttribute(ORDERS, orderService.getInProgressOrdersByUser(user));
            model.addAttribute(PRODUCTS_COUNT, user.getCart().getProductsCount());
        }

        return "index";
    }
}
