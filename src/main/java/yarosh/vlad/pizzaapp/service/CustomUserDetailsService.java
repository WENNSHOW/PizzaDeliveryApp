package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.entity.Role;
import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static yarosh.vlad.pizzaapp.constant.ErrorMessages.USER_NOT_FOUND_WITH_EMAIL;
import static yarosh.vlad.pizzaapp.constant.Messages.ROLE;

@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findUserByEmailIgnoreCase(email)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_WITH_EMAIL + email));
    }

    private UserDetails mapToUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                extractAuthorities(user));
    }

    private List<GrantedAuthority> extractAuthorities(User user) {
        return user
                .getRoles()
                .stream()
                .map(this::mapRole)
                .toList();
    }

    private GrantedAuthority mapRole(Role role) {
        return new SimpleGrantedAuthority(ROLE + role.getRole().name());
    }
}
