package yarosh.vlad.pizzaapp.repository;

import yarosh.vlad.pizzaapp.domain.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
