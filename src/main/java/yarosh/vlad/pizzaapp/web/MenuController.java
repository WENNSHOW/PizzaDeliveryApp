package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.constant.ProductCategoryEnum;
import yarosh.vlad.pizzaapp.exception.WrongCategoryException;
import yarosh.vlad.pizzaapp.service.ProductService;
import yarosh.vlad.pizzaapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static yarosh.vlad.pizzaapp.constant.ControllerConstants.*;

@Controller
@AllArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public String getMenu(Principal principal, Model model) {
        if (principal != null) {
            model.addAttribute(PRODUCTS_COUNT, this.userService
                    .getUserByEmail(principal.getName())
                    .getCart()
                    .getProductsCount());
        }

        return "menu-categories";
    }

    @GetMapping("/{category}")
    public String getCategoryPage(@PathVariable String category, Model model, Principal principal) {
        model.addAttribute(CATEGORY, this.productService.findCategory(category));
        model.addAttribute(PRODUCTS, this.productService.getAllProductsByCategory(ProductCategoryEnum.valueOf(category)));

        if (principal != null) {
            model.addAttribute(PRODUCTS_COUNT, this.userService
                    .getUserByEmail(principal.getName())
                    .getCart()
                    .getProductsCount());
        }

        return "categories-page";
    }

    @ExceptionHandler(WrongCategoryException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView categoryDoesNotExist(WrongCategoryException wrongCategoryException) {
        ModelAndView modelAndView = new ModelAndView("category-does-not-exist");
        modelAndView.addObject(CATEGORY, wrongCategoryException.getCategory());
        return modelAndView;
    }
}
