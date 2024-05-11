package yarosh.vlad.pizzaapp.service.helper;

import yarosh.vlad.pizzaapp.domain.dto.binding.AddProductBindingDto;
import yarosh.vlad.pizzaapp.domain.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceHelper {

    public static Product createProduct(AddProductBindingDto addProductBindingDto) {
        Product product = new Product();
        product
                .setName(addProductBindingDto.getName())
                .setCategory(addProductBindingDto.getCategory())
                .setDescription(addProductBindingDto.getDescription())
                .setPrice(addProductBindingDto.getPrice());
        return product;
    }
}
