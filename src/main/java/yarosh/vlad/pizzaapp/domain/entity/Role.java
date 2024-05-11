package yarosh.vlad.pizzaapp.domain.entity;

import yarosh.vlad.pizzaapp.domain.constant.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
