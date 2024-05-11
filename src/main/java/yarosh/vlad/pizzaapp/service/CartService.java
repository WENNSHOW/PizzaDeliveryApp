package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.entity.Cart;
import yarosh.vlad.pizzaapp.domain.entity.Product;
import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.exception.ObjectNotFoundException;
import yarosh.vlad.pizzaapp.exception.ResourceNotFoundException;
import yarosh.vlad.pizzaapp.repository.CartRepository;
import yarosh.vlad.pizzaapp.repository.ProductRepository;
import yarosh.vlad.pizzaapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yarosh.vlad.pizzaapp.constant.ControllerConstants;

@Service
@AllArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void addToCart(Long id, String email) {
        User user = this.userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException(ControllerConstants.USER, ControllerConstants.EMAIL, email));
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ControllerConstants.PRODUCT, ControllerConstants.ID, id));

        user.getCart().addProduct(product);
        user.getCart().increaseProductsSum(product.getPrice());
    }

    @Transactional
    public void removeFromCart(Long id, String email) {
        User user = this.userRepository.findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResourceNotFoundException(ControllerConstants.USER, ControllerConstants.EMAIL, email));
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ControllerConstants.PRODUCT, ControllerConstants.ID, id));

        user.getCart().getProducts().remove(product);
        user.getCart().decreaseProductsSum(product.getPrice());
    }

    public Cart getNewCart() {
        Cart cart = new Cart();
        this.cartRepository.saveAndFlush(cart);
        return cart;
    }
}
