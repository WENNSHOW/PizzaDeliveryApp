package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.entity.User;
import yarosh.vlad.pizzaapp.util.TestDataUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static yarosh.vlad.pizzaapp.constant.ControllerConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {
        User testAdmin = testDataUtils.testCreateAdmin("adminMenu@admin.com", "adminMenu");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    void testGetMenu() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu-categories"));
    }

    @Test
    @WithMockUser(username = "adminMenu@admin.com", roles = {"USER", "ADMIN", "WORKER"})
    void testGetMenu_LoggedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu-categories"))
                .andExpect(model().attributeExists(PRODUCTS_COUNT));
    }

    @Test
    void testGetMenuCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/pizza"))
                .andExpect(view().name("categories-page"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(CATEGORY, PRODUCTS));
    }

    @Test
    @WithMockUser(username = "adminMenu@admin.com", roles = {"USER", "ADMIN", "WORKER"})
    void testGetMenuCategories_LoggedUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/pizza"))
                .andExpect(view().name("categories-page"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(CATEGORY, PRODUCTS));
    }

    @Test
    void testGetMenuCategories_NotSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/menu/non-existent"))
                .andExpect(view().name("category-does-not-exist"))
                .andExpect(status().is4xxClientError());
    }
}
