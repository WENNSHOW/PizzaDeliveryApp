package yarosh.vlad.pizzaapp.web;

import yarosh.vlad.pizzaapp.domain.entity.Order;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    private User testUser, testAdmin;

    private Order order;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.testCreateUser("userOrder@user.com", "userOrder");
        testAdmin = testDataUtils.testCreateAdmin("adminOrder@admin.com", "adminOrder");
        order = testDataUtils.testCreateOrder(testUser);
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testGetFinalize() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/orders/finalize"))
                .andExpect(status().isOk())
                .andExpect(view().name("finalize-order"))
                .andExpect(model().attributeExists(FOOD_PRICE, PRODUCTS_COUNT));

    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testFinalizeOrder() throws Exception {
        mockMvc.perform(post("/orders/finalize")
                        .param("comment", "Test comment")
                        .param("address", "Test address1")
                        .param("discount", "TOPPIZZA")
                        .param("contactPhoneNumber", "0887080808")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testFinalizeOrder_InvalidData() throws Exception {
        mockMvc.perform(post("/orders/finalize")
                        .param("comment", "Test comment")
                        .param("address", "Test address1")
                        .param("discount", "")
                        .param("contactPhoneNumber", "")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/orders/finalize"));
    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testGetOrdersHistory() throws Exception {
        mockMvc.perform(get("/orders/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders-history-user"))
                .andExpect(model().attributeExists(ORDERS));
    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testGetOrderDetails() throws Exception {
        mockMvc.perform(get("/orders/details/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("order-details-api"))
                .andExpect(model().attributeExists(ORDER, ID_ATTRIBUTE));
    }

    @Test
    @WithMockUser(username = "userOrder@user.com", roles = "USER")
    void testGetOrderDetails_NotSuccessful() throws Exception {
        mockMvc.perform(get("/orders/details/789"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("id-not-found"))
                .andExpect(model().attributeExists(RESOURCE_NAME, FIELD_NAME, FIELD_VALUE));
    }

    @Test
    @WithMockUser(username = "adminOrder@admin.com", roles = {"USER", "ADMIN", "WORKER"})
    void testGetAllOrdersHistory() throws Exception {
        mockMvc.perform(get("/orders/all/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders-history"))
                .andExpect(model().attributeExists("allOrders"));
    }
}
