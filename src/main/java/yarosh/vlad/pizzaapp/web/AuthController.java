package yarosh.vlad.pizzaapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthController {

    @GetMapping("/login")
    public String getLogin() {
        return "auth-login";
    }

    @PostMapping("/login-error")
    public String failedLogin(
            @ModelAttribute("email")
            String email,
            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("email", email);
        redirectAttributes.addFlashAttribute("bad_credentials", true);
        return "redirect:/users/login";
    }
}
