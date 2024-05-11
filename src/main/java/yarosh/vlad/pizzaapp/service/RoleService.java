package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import yarosh.vlad.pizzaapp.domain.entity.Role;
import yarosh.vlad.pizzaapp.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRole(RoleEnum roleEnum) {
        Optional<Role> optionalRole = this.roleRepository.findByRole(roleEnum);
        Role role = new Role();
        if (optionalRole.isPresent()) {
            role = optionalRole.get();
        }
        return role;
    }
}
