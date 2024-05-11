package yarosh.vlad.pizzaapp.domain.dto.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static yarosh.vlad.pizzaapp.constant.ErrorMessages.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProductBindingDto {

    @NotEmpty(message = DESCRIPTION_NOT_EMPTY)
    private String description;

    @Positive(message = POSITIVE_PRICE)
    @NotNull(message = PRICE_REQUIRED)
    private BigDecimal price;
}
