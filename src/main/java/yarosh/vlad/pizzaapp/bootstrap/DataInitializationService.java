package yarosh.vlad.pizzaapp.bootstrap;

import yarosh.vlad.pizzaapp.domain.constant.GenderEnum;
import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import yarosh.vlad.pizzaapp.domain.entity.Role;
import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.repository.RoleRepository;
import yarosh.vlad.pizzaapp.repository.UserRepository;
import yarosh.vlad.pizzaapp.service.CartService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DataInitializationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    private static List<Role> getUserRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleEnum.ADMIN));
        roles.add(new Role(RoleEnum.USER));
        roles.add(new Role(RoleEnum.WORKER));
        return roles;
    }

    @PostConstruct
    public void init() {
        initRoles();
        initAdmin();
        initUsers();
        initWorkers();
    }

    public void initRoles() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAllAndFlush(getUserRoles());
        }
    }

    public void initAdmin() {
        if (this.userRepository.count() == 0) {
            User user = new User()
                    .setFirstName("Admin")
                    .setLastName("Admin")
                    .setAge(14)
                    .setEmail("admin@gmail.com")
                    .setPassword(passwordEncoder.encode("!Admin123"))
                    .setGender(GenderEnum.MALE)
                    .setUsername("admin")
                    .setPhoneNumber("0707070707")
                    .setRoles(roleRepository.findAll())
                    .setCart(this.cartService.getNewCart());
            this.userRepository.saveAndFlush(user);
        }
    }

    private void initUsers() {
        if (this.userRepository.count() == 1) {

            User userOne = new User()
                    .setFirstName("Vladislav")
                    .setLastName("Yarosh")
                    .setAge(19)
                    .setEmail("vlad@gmail.com")
                    .setPassword(passwordEncoder.encode("!Vlad123"))
                    .setGender(GenderEnum.MALE)
                    .setUsername("wennshow")
                    .setPhoneNumber("0887080808")
                    .setRoles(roleRepository.findAll()
                            .stream()
                            .filter(r -> r.getRole() == RoleEnum.USER)
                            .collect(Collectors.toList()))
                    .setCart(this.cartService.getNewCart());

            this.userRepository.saveAndFlush(userOne);

            User userTwo = new User()
                    .setFirstName("Alexandr")
                    .setLastName("Parpulanschi")
                    .setAge(22)
                    .setEmail("alex@gmail.com")
                    .setPassword(passwordEncoder.encode("!Alex123"))
                    .setGender(GenderEnum.MALE)
                    .setUsername("alex")
                    .setPhoneNumber("0887090909")
                    .setRoles(roleRepository.findAll()
                            .stream()
                            .filter(r -> r.getRole() == RoleEnum.USER)
                            .collect(Collectors.toList()))
                    .setCart(this.cartService.getNewCart());

            this.userRepository.saveAndFlush(userTwo);

            User userThree = new User()
                    .setFirstName("Emilia")
                    .setLastName("Georgieva")
                    .setAge(28)
                    .setEmail("emilia@gmail.com")
                    .setPassword(passwordEncoder.encode("!Emilia123"))
                    .setGender(GenderEnum.FEMALE)
                    .setUsername("emi")
                    .setPhoneNumber("0887040404")
                    .setRoles(roleRepository.findAll()
                            .stream()
                            .filter(r -> r.getRole() == RoleEnum.USER)
                            .collect(Collectors.toList()))
                    .setCart(this.cartService.getNewCart());

            this.userRepository.saveAndFlush(userThree);
        }
    }

    public void initWorkers() {
        if (this.userRepository.count() == 4) {

            User workerOne = new User()
                    .setFirstName("Yulia")
                    .setLastName("Tenincheva")
                    .setAge(35)
                    .setEmail("yulia@gmail.com")
                    .setPassword(passwordEncoder.encode("!Yulia123"))
                    .setGender(GenderEnum.FEMALE)
                    .setUsername("yuli")
                    .setPhoneNumber("0887010101")
                    .setRoles(roleRepository.findAll()
                            .stream()
                            .filter(r -> r.getRole() == RoleEnum.WORKER)
                            .collect(Collectors.toList()))
                    .setCart(this.cartService.getNewCart());

            this.userRepository.saveAndFlush(workerOne);

            User workerTwo = new User()
                    .setFirstName("Sinan")
                    .setLastName("Niyaziev")
                    .setAge(31)
                    .setEmail("sinan@gmail.com")
                    .setPassword(passwordEncoder.encode("!Sinan123"))
                    .setGender(GenderEnum.MALE)
                    .setUsername("sinan")
                    .setPhoneNumber("0887020202")
                    .setRoles(roleRepository.findAll()
                            .stream()
                            .filter(r -> r.getRole() == RoleEnum.WORKER)
                            .collect(Collectors.toList()))
                    .setCart(this.cartService.getNewCart());

            this.userRepository.saveAndFlush(workerTwo);
        }
    }
}
