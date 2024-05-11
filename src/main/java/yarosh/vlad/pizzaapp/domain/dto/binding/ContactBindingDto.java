package yarosh.vlad.pizzaapp.domain.dto.binding;

import yarosh.vlad.pizzaapp.validation.common.ValidEmail;
import yarosh.vlad.pizzaapp.validation.common.ValidPersonName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import static yarosh.vlad.pizzaapp.constant.ErrorMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ContactBindingDto {

    @NotEmpty(message = NAME_REQUIRED)
    @ValidPersonName
    private String name;

    @NotEmpty(message = EMAIL_REQUIRED)
    @ValidEmail
    private String email;

    @NotEmpty(message = SUBJECT_REQUIRED)
    @Size(min = 2, message = SUBJECT_MINIMUM)
    private String subject;

    @NotEmpty(message = DESCRIPTION_REQUIRED)
    @Size(min = 10, max = 2000, message = DESCRIPTION_BETWEEN_10_2000)
    private String description;
}
