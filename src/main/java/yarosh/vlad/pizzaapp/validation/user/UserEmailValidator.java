package yarosh.vlad.pizzaapp.validation.user;

import yarosh.vlad.pizzaapp.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserEmailValidator implements ConstraintValidator<ValidUserEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return this.userRepository
                .findUserByEmailIgnoreCase(email)
                .isEmpty();
    }
}
