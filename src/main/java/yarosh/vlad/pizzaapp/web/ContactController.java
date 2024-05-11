package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.dto.binding.ContactBindingDto;
import yarosh.vlad.pizzaapp.service.ContactService;
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
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    @ModelAttribute("contactDto")
    public ContactBindingDto initBindingDto() {
        return new ContactBindingDto();
    }

    @GetMapping
    public String getContact() {
        return "contact-us";
    }

    @PostMapping
    public String postContact(
            @Valid ContactBindingDto contactBindingDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contactDto", contactBindingDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contactDto", bindingResult);
            return "redirect:/contact";
        }

        this.contactService.saveContactMessage(contactBindingDto);
        return "redirect:/";
    }
}
