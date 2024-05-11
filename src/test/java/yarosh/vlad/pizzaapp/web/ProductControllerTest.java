package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.entity.Product;
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
import static yarosh.vlad.pizzaapp.constant.ControllerConstants.FIELD_VALUE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private Product pizza1, pizza2, pizza3;

    @BeforeEach
    void setUp() {
        pizza1 = testDataUtils.testCreateProductPizza("pizza1");
        pizza2 = testDataUtils.testCreateProductPizza("pizza2");
        pizza3 = testDataUtils.testCreateProductPizza("pizza3");
    }

    @AfterEach
    void tearDown(){
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetAddProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-product"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddProduct() throws Exception {
        mockMvc.perform(post("/products/add")
                        .param("name", "Test pizza")
                        .param("description", "Test pizza description")
                        .param("category", "pizza")
                        .param("price", "10.00")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testAddProduct_InvalidData() throws Exception {
        mockMvc.perform(post("/products/add")
                        .param("name", "")
                        .param("description", "Test pizza description")
                        .param("category", "pizza")
                        .param("price", "10.00")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/add"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetEditProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/edit/{id}", pizza1.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-product"))
                .andExpect(model().attributeExists(PRODUCT));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetEditProduct_NotSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/edit/28"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("id-not-found"))
                .andExpect(model().attributeExists(RESOURCE_NAME, FIELD_NAME, FIELD_VALUE));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/products/delete/{id}", pizza2.getId())
                        .param("id", pizza2.getId().toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu/pizza"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/edited/{id}", pizza3.getId())
                        .with(csrf())
                        .param("description", "new descriptionnn")
                        .param("price", "600"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu/pizza"));

    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testUpdateProduct_InvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/products/edited/{id}", pizza3.getId())
                        .with(csrf())
                        .param("description", "dddddd")
                        .param("price", "-600"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/products/edit/" + pizza3.getId().toString()));

    }
}