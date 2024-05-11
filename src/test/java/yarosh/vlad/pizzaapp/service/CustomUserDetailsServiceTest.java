package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.constant.GenderEnum;
import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import yarosh.vlad.pizzaapp.domain.entity.Role;
import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    void testLoadUserByEmail_UserExists() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(RoleEnum.ADMIN));
        roles.add(new Role(RoleEnum.USER));
        roles.add(new Role(RoleEnum.WORKER));

        User testUser = new User()
                .setUsername("admin")
                .setPassword("!Admin123")
                .setEmail("admin@gmail.com")
                .setAge(14)
                .setPhoneNumber("0707070707")
                .setFirstName("Admin")
                .setLastName("Admin")
                .setGender(GenderEnum.MALE)
                .setRoles(roles);

        when(userRepository.findUserByEmailIgnoreCase(testUser.getEmail()))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(testUser.getEmail());

        Assertions.assertNotNull(userDetails);

        Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());

        Assertions.assertEquals(3, userDetails.getAuthorities().size());
        assertRole(userDetails.getAuthorities(), "ROLE_ADMIN");
        assertRole(userDetails.getAuthorities(), "ROLE_WORKER");
        assertRole(userDetails.getAuthorities(), "ROLE_USER");
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities, String role) {
        authorities.
                stream().
                filter(a -> role.equals(a.getAuthority())).
                findAny().
                orElseThrow(() -> new AssertionFailedError(role + " not found!"));
    }

    @Test
    void testLoadUserByEmail_UserDoesNotExist() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("non-existant@example.com")
        );
    }
}
