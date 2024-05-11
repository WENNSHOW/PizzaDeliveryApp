package yarosh.vlad.pizzaapp.service;

import yarosh.vlad.pizzaapp.domain.dto.binding.ContactBindingDto;
import yarosh.vlad.pizzaapp.domain.entity.Contact;
import yarosh.vlad.pizzaapp.repository.ContactRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ContactRepository contactRepository;

    @Captor
    private ArgumentCaptor<Contact> contactArgumentCaptor;

    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(modelMapper, contactRepository);
    }

    @Test
    void testContactSave() {
        ContactBindingDto contactBindingDto = new ContactBindingDto()
                .setName("contact")
                .setSubject("subject")
                .setEmail("email@email.com")
                .setDescription("description");

        contactService.saveContactMessage(contactBindingDto);
        verify(contactRepository).saveAndFlush(contactArgumentCaptor.capture());

        Contact contact = contactArgumentCaptor.getValue();

        Assertions.assertEquals(contactBindingDto.getName(), contact.getName());
        Assertions.assertEquals(contactBindingDto.getDescription(), contact.getDescription());
        Assertions.assertEquals(contactBindingDto.getSubject(), contact.getSubject());
        Assertions.assertEquals(contactBindingDto.getEmail(), contact.getEmail());

    }

}
