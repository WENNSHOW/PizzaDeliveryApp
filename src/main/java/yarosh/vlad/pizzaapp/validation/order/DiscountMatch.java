package yarosh.vlad.pizzaapp.validation.order;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import yarosh.vlad.pizzaapp.constant.ErrorMessages;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DiscountMatchValidator.class)
public @interface DiscountMatch {

    String message() default ErrorMessages.INVALID_DISCOUNT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
