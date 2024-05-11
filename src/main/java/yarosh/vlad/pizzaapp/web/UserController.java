package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.dto.binding.EditUserBindingDto;
import yarosh.vlad.pizzaapp.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ModelAttribute("editedUser")
    public EditUserBindingDto initBindingDto() {
        return new EditUserBindingDto();
    }

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal) {
        model.addAttribute(USER, this.userService.getUserViewDtoByEmail(principal.getName()));
        model.addAttribute(PRODUCTS_COUNT, this.userService
                .getUserByEmail(principal.getName())
                .getCart()
                .getProductsCount());
        return "user-profile";
    }

    @GetMapping("/profile/{id}")
    public String getProfileById(@PathVariable Long id, Model model) {
        model.addAttribute(USER, this.userService.getUserById(id));
        return "user-profile";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute(USERS, this.userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/edit/{id}")
    public String getEditUser(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute(USER, this.userService.getUserById(id));
        model.addAttribute(PRODUCTS_COUNT, this.userService
                .getUserByEmail(principal.getName())
                .getCart()
                .getProductsCount());
        return "edit-user";
    }

    @PatchMapping("/edited/{id}")
    public String editedUser(
            @PathVariable Long id,
            @Valid EditUserBindingDto editUserBindingDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editedUser", editUserBindingDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editedUser", bindingResult);
            return "redirect:/users/edit/{id}";
        }

        this.userService.editUser(id, editUserBindingDto);
        return "redirect:/users/profile";
    }

    @GetMapping("/change/{id}")
    public String changeRole(@PathVariable Long id, Model model) {
        model.addAttribute(USER, this.userService.getUserById(id));
        return "roles-change";
    }

    @PatchMapping("/roles/add/{id}")
    public String addRole(@PathVariable("id") Long id) {

        this.userService.addRole(id);
        return "redirect:/users/change/{id}";
    }

    @PatchMapping("/roles/remove/{id}")
    public String removeRole(@PathVariable Long id) {
        this.userService.removeRole(id);
        return "redirect:/users/change/{id}";
    }
}
