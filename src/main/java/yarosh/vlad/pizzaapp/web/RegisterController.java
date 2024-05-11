package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.dto.binding.RegistrationBindingDto;
import yarosh.vlad.pizzaapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class RegisterController {

    private final UserService userService;

    @ModelAttribute("registerDto")
    public RegistrationBindingDto initBindingDto() {
        return new RegistrationBindingDto();
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(
            @Valid RegistrationBindingDto registrationBindingDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDto", registrationBindingDto);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
            return "redirect:/users/register";
        }

        this.userService.register(registrationBindingDto);
        return "redirect:/users/login";
    }
}
