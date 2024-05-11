package yarosh.vlad.pizzaapp.validation.product;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidProductNameValidator implements ConstraintValidator<ValidProductName, String> {

    @Override
    public boolean isValid(String productName, ConstraintValidatorContext context) {
        if (productName.length() < 4) {
            return false;
        }

        for (int i = 0; i < productName.length(); i++) {
            if (!Character.isLetter(productName.charAt(i)) &&
                    !Character.isWhitespace(productName.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}
