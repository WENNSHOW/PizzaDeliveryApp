package yarosh.vlad.pizzaapp.domain.dto.binding;

import yarosh.vlad.pizzaapp.domain.constant.ProductCategoryEnum;
import yarosh.vlad.pizzaapp.validation.product.ValidProductName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class AddProductBindingDto {

    @NotEmpty(message = NAME_REQUIRED)
    @ValidProductName
    private String name;

    @Positive(message = POSITIVE_PRICE)
    @NotNull(message = PRICE_REQUIRED)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductCategoryEnum category;

    @NotEmpty(message = DESCRIPTION_REQUIRED)
    private String description;
}
