package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.entity.Product;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User user;

    private Product burgerOne, pizzaOne;

    @BeforeEach
    void setUp() {
        user = testDataUtils.testCreateUser("user1@user.com", "user1");
        burgerOne = testDataUtils.testCreateProductBurger("burgerOne");
        pizzaOne = testDataUtils.testCreateProductPizza("pizzaOne");
        testDataUtils.testAddProduct(user, burgerOne);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }


    @Test
    @WithMockUser(username = "user1@user.com", roles = "USER")
    void testGetCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cart"))
                .andExpect(view().name("order-cart"))
                .andExpect(model().attributeExists(CART_PRODUCTS, PRODUCTS_PRICE, PRODUCTS_COUNT))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1@user.com", roles = "USER")
    void testAddProductToCart() throws Exception {
        mockMvc.perform(patch("/cart/add/{id}", pizzaOne.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/menu/pizza"));

    }

    @Test
    @WithMockUser(username = "user1@user.com", roles = "USER")
    void testRemoveFromCart() throws Exception {
        mockMvc.perform(patch("/cart/remove/{id}", burgerOne.getId())
                        .param("id", burgerOne.getId().toString())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cart"));
    }
}