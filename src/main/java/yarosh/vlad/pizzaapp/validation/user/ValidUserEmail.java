package yarosh.vlad.pizzaapp.validation.user;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import yarosh.vlad.pizzaapp.constant.ErrorMessages;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UserEmailValidator.class)
public @interface ValidUserEmail {

    String message() default ErrorMessages.INVALID_USERNAME;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
