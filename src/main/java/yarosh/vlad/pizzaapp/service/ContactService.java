package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.dto.binding.ContactBindingDto;
import yarosh.vlad.pizzaapp.domain.entity.Contact;
import yarosh.vlad.pizzaapp.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import yarosh.vlad.pizzaapp.constant.Messages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@AllArgsConstructor
public class ContactService {

    private final ModelMapper modelMapper;
    private final ContactRepository contactRepository;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Messages.DATE_TIME_NOW_PATTERN);

    public void saveContactMessage(ContactBindingDto contactBindingDto) {
        Contact contact = this.modelMapper.map(contactBindingDto, Contact.class);

        contact.setCreatedOn(LocalDateTime.parse(dateTimeFormatter.format(LocalDateTime.now()), dateTimeFormatter));
        this.contactRepository.saveAndFlush(contact);
    }
}
