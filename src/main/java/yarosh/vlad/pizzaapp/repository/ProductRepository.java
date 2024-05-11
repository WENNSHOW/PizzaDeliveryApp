package yarosh.vlad.pizzaapp.repository;

import yarosh.vlad.pizzaapp.domain.constant.ProductCategoryEnum;
import yarosh.vlad.pizzaapp.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllProductsByCategory(ProductCategoryEnum productCategoryEnum);

    Product findProductById(Long id);

    Product findProductByName(String name);
}
