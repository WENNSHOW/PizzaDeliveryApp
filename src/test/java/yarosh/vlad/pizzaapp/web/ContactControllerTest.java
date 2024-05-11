package yarosh.vlad.pizzaapp.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetContact() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("contact-us"));
    }

    @Test
    void testPostContact() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("name", "Daniel")
                        .param("email", "email@gmail.com")
                        .param("subject", "Test subject")
                        .param("description", "Test description")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void testPostContact_InvalidData() throws Exception {
        mockMvc.perform(post("/contact")
                        .param("name", "Daniel")
                        .param("email", "email@gmail.com")
                        .param("subject", "Test subject")
                        .param("description", "T")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact"));

    }

}

