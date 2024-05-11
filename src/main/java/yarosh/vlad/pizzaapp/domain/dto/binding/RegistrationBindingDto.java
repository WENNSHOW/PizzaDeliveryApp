package yarosh.vlad.pizzaapp.domain.dto.binding;

import yarosh.vlad.pizzaapp.domain.constant.GenderEnum;
import yarosh.vlad.pizzaapp.validation.common.ValidEmail;
import yarosh.vlad.pizzaapp.validation.common.ValidPersonName;
import yarosh.vlad.pizzaapp.validation.common.ValidPhoneNumber;
import yarosh.vlad.pizzaapp.validation.user.PasswordValueMatches;
import yarosh.vlad.pizzaapp.validation.user.ValidPassword;
import yarosh.vlad.pizzaapp.validation.user.ValidUserEmail;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static yarosh.vlad.pizzaapp.constant.ErrorMessages.*;
import static yarosh.vlad.pizzaapp.constant.Messages.CONFIRM_PASSWORD;
import static yarosh.vlad.pizzaapp.constant.Messages.PASSWORD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordValueMatches.List({
        @PasswordValueMatches(
                field = PASSWORD,
                fieldMatch = CONFIRM_PASSWORD
        )
})
public class RegistrationBindingDto {

    @NotEmpty(message = FIRST_NAME_REQUIRED)
    @ValidPersonName
    private String firstName;

    @NotEmpty(message = LAST_NAME_REQUIRED)
    @ValidPersonName
    private String lastName;

    @NotEmpty(message = USERNAME_REQUIRED)
    @Size(min = 4, message = USERNAME_MINIMUM)
    private String username;

    @NotEmpty(message = EMAIL_REQUIRED)
    @ValidUserEmail(message = EMAIL_UNIQUE)
    @ValidEmail
    private String email;

    @NotEmpty(message = PASSWORD_REQUIRED)
    @Size(min = 8, message = PASSWORD_MINIMUM)
    @ValidPassword
    private String password;

    @ValidPassword
    private String confirmPassword;

    @Positive
    @NotNull(message = AGE_REQUIRED)
    private Integer age;

    @NotEmpty(message = PHONE_NUMBER_REQUIRED)
    @ValidPhoneNumber
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private GenderEnum gender;
}
