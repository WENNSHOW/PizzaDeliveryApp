package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.entity.Product;
import yarosh.vlad.pizzaapp.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import yarosh.vlad.pizzaapp.constant.Messages;

import java.math.BigDecimal;
import java.time.DayOfWeek;

@Service
@AllArgsConstructor
public class PromotionService {

    private final ProductRepository productRepository;

    public void makePromotions(DayOfWeek dayOfWeek) {
        Product margherita = this.productRepository.findProductByName(Messages.MARGHERITA);
        Product pepperoni = this.productRepository.findProductByName(Messages.PEPPERONI);
        Product capricciosa = this.productRepository.findProductByName(Messages.CAPRICCIOSA);
        Product chickenBurger = this.productRepository.findProductByName(Messages.CHICKEN_BURGER);
        Product veganBurger = this.productRepository.findProductByName(Messages.VEGAN_BURGER);
        Product satoshi = this.productRepository.findProductByName(Messages.SATOSHI);
        Product sweetFries = this.productRepository.findProductByName(Messages.SWEET_FRIES);
        Product brownie = this.productRepository.findProductByName(Messages.BROWNIE);
        Product doughnuts = this.productRepository.findProductByName(Messages.DOUGHNUTS);
        Product mousse = this.productRepository.findProductByName(Messages.MOUSSE);

        switch (dayOfWeek) {
            case MONDAY -> monday(margherita, brownie, mousse);
            case TUESDAY -> tuesday(chickenBurger, sweetFries);
            case WEDNESDAY -> wednesday(veganBurger, pepperoni);
            case THURSDAY -> thursday(satoshi, doughnuts);
            case FRIDAY -> friday(capricciosa, margherita, pepperoni);
            case SATURDAY -> saturday(chickenBurger, satoshi, brownie, mousse);
        }
    }

    private void monday(Product margherita,
                        Product brownie,
                        Product mousse) {
        margherita.setPrice(BigDecimal.valueOf(6.99));
        this.productRepository.save(margherita);

        brownie.setPrice(BigDecimal.valueOf(3.59));
        this.productRepository.save(brownie);

        mousse.setPrice(BigDecimal.valueOf(2.99));
        this.productRepository.save(mousse);
    }

    private void tuesday(Product chickenBurger,
                         Product sweetFries) {
        chickenBurger.setPrice(BigDecimal.valueOf(9.99));
        this.productRepository.save(chickenBurger);

        sweetFries.setPrice(BigDecimal.valueOf(5.99));
        this.productRepository.save(sweetFries);
    }

    private void wednesday(Product veganBurger,
                           Product pepperoni) {
        veganBurger.setPrice(BigDecimal.valueOf(10.99));
        this.productRepository.save(veganBurger);

        pepperoni.setPrice(BigDecimal.valueOf(9.99));
        this.productRepository.save(pepperoni);
    }

    private void thursday(Product satoshi,
                          Product doughnuts) {
        satoshi.setPrice(BigDecimal.valueOf(12.99));
        this.productRepository.save(satoshi);

        doughnuts.setPrice(BigDecimal.valueOf(1.09));
        this.productRepository.save(doughnuts);
    }

    private void friday(Product capricciosa,
                        Product margherita,
                        Product pepperoni) {
        capricciosa.setPrice(BigDecimal.valueOf(7.99));
        this.productRepository.save(capricciosa);

        margherita.setPrice(BigDecimal.valueOf(5.99));
        this.productRepository.save(margherita);

        pepperoni.setPrice(BigDecimal.valueOf(8.99));
        this.productRepository.save(pepperoni);
    }

    private void saturday(Product chickenBurger,
                          Product satoshi,
                          Product brownie,
                          Product mousse) {
        chickenBurger.setPrice(BigDecimal.valueOf(9.99));
        this.productRepository.save(chickenBurger);

        satoshi.setPrice(BigDecimal.valueOf(13.99));
        this.productRepository.save(satoshi);

        brownie.setPrice(BigDecimal.valueOf(2.99));
        this.productRepository.save(brownie);

        mousse.setPrice(BigDecimal.valueOf(2.49));
        this.productRepository.save(mousse);
    }
}
