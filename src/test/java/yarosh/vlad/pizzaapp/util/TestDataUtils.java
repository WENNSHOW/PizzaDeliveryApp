package yarosh.vlad.pizzaapp.util;

import yarosh.vlad.pizzaapp.domain.constant.GenderEnum;
import yarosh.vlad.pizzaapp.domain.constant.OrderStatusEnum;
import yarosh.vlad.pizzaapp.domain.constant.ProductCategoryEnum;
import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import yarosh.vlad.pizzaapp.domain.dto.view.OrderDetailsViewDto;
import yarosh.vlad.pizzaapp.domain.entity.*;
import yarosh.vlad.pizzaapp.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class TestDataUtils {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public TestDataUtils(RoleRepository roleRepository,
                         UserRepository userRepository,
                         CartRepository cartRepository,
                         ProductRepository productRepository,
                         OrderRepository orderRepository,
                         ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    private void initRoles() {
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setRole(RoleEnum.ADMIN);
            Role userRole = new Role();
            userRole.setRole(RoleEnum.USER);
            Role workerRole = new Role();
            workerRole.setRole(RoleEnum.WORKER);

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
            roleRepository.save(workerRole);
        }
    }

    public User testCreateAdmin(String email, String username) {
        initRoles();

        User admin = new User()
                .setEmail(email)
                .setUsername(username)
                .setFirstName("Admin")
                .setLastName("Admin")
                .setPassword("!Admin123")
                .setGender(GenderEnum.FEMALE)
                .setAge(14)
                .setCart(testCreateCart())
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0707070707")
                .setRoles(roleRepository.findAll());

        return userRepository.save(admin);
    }

    public void testAddProduct(User user, Product product) {
        user.getCart().addProduct(product);
    }


    public User testCreateUser(String email, String username) {
        initRoles();

        User user = new User()
                .setEmail(email)
                .setUsername(username)
                .setFirstName("Vlad")
                .setLastName("Yarosh")
                .setPassword("!Vlad123")
                .setGender(GenderEnum.MALE)
                .setAge(27)
                .setCart(testCreateCart())
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0887080808")
                .setRoles(roleRepository.
                        findAll().stream().
                        filter(r -> r.getRole() == RoleEnum.USER).
                        toList());

        return userRepository.save(user);
    }

    public User testCreateWorker(String email, String username) {
        initRoles();

        User user = new User()
                .setEmail(email)
                .setUsername(username)
                .setFirstName("Yulia")
                .setLastName("Tenincheva")
                .setPassword("!Yulia123")
                .setGender(GenderEnum.FEMALE)
                .setAge(35)
                .setCart(testCreateCart())
                .setOrders(new ArrayList<>())
                .setPhoneNumber("0887010101")
                .setRoles(roleRepository.
                        findAll().stream().
                        filter(r -> r.getRole() != RoleEnum.WORKER).
                        toList());

        return userRepository.save(user);
    }

    public OrderDetailsViewDto testCreateOrderDetailsViewDto(String ownerEmail, String ownerName) {
        Order entity = new Order()
                .setOwner(testCreateUser(ownerEmail, ownerName))
                .setPrice(BigDecimal.TEN)
                .setAddress("address")
                .setCreatedOn(LocalDateTime.now())
                .setContactPhoneNumber("contactPhoneNumber")
                .setStatus(OrderStatusEnum.IN_PROGRESS);

        return this.modelMapper.map(entity, OrderDetailsViewDto.class);
    }


    public Cart testCreateCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);

    }

    public Product testCreateProductBurger(String name) {
        Product product = new Product()
                .setPrice(BigDecimal.TEN)
                .setName(name)
                .setCategory(ProductCategoryEnum.burger)
                .setDescription("description product");

        return productRepository.saveAndFlush(product);
    }

    public Product testCreateProductPizza(String name) {
        Product product = new Product()
                .setPrice(BigDecimal.TEN)
                .setName(name)
                .setCategory(ProductCategoryEnum.pizza)
                .setDescription("description product");

        return productRepository.saveAndFlush(product);
    }

    public Order testCreateOrder(User owner) {

        Order order = new Order()
                .setPrice(BigDecimal.TEN)
                .setCreatedOn(LocalDateTime.now())
                .setAddress("address")
                .setContactPhoneNumber("0887080808")
                .setComment("comment")
                .setOwner(owner)
                .setStatus(OrderStatusEnum.IN_PROGRESS);

        return orderRepository.save(order);
    }

    public void cleanUpDatabase() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        cartRepository.deleteAll();
        productRepository.deleteAll();
        roleRepository.deleteAll();
    }
}
