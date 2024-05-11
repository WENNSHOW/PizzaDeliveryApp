package yarosh.vlad.pizzaapp.validation.order;

import yarosh.vlad.pizzaapp.domain.constant.DiscountEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class DiscountMatchValidator implements ConstraintValidator<DiscountMatch, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }

        long count = Arrays.stream(DiscountEnum.values())
                .map(Enum::name)
                .filter(discount -> discount.equals(value))
                .count();
        return count != 0;
    }
}
