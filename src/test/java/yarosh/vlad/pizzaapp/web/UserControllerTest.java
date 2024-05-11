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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    User testUser, testAdmin, testWorker;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestDataUtils testDataUtils;

    @BeforeEach
    void setUp() {
        testUser = testDataUtils.testCreateUser("userControl@user.com", "testUser");
        testAdmin = testDataUtils.testCreateAdmin("adminUserControl@admin.com", "adminUser");
        testWorker = testDataUtils.testCreateWorker("workerUser@worker.com", "workerUser");
    }

    @AfterEach
    void tearDown() {
        testDataUtils.cleanUpDatabase();
    }

    @Test
    @WithMockUser(username = "userControl@user.com", roles = "USER")
    void testGetUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists(USER));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile/{id}", testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(model().attributeExists(USER));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testGetUserById_NotSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/profile/896"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("id-not-found"))
                .andExpect(model().attributeExists(RESOURCE_NAME, FIELD_NAME, FIELD_VALUE));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-users"))
                .andExpect(model().attributeExists(USERS));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testGetChangeRoles() throws Exception {
        mockMvc.perform(get("/users/change/{id}", testUser.getId()))
                .andExpect(view().name("roles-change"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(USER));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testGetChangeRoles_NotSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/change/896"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("id-not-found"))
                .andExpect(model().attributeExists(RESOURCE_NAME, FIELD_NAME, FIELD_VALUE));
    }

    @Test
    @WithMockUser(username = "workerUser@worker.com", roles = {"WORKER", "USER"})
    void testGetEditUser() throws Exception {
        mockMvc.perform(get("/users/edit/{id}", testUser.getId()))
                .andExpect(view().name("edit-user"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists(USER, PRODUCTS_COUNT));
    }

    @Test
    @WithMockUser
    void testGetEditUser_NotSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/edit/28"))
                .andExpect(status().is4xxClientError())
                .andExpect(view().name("id-not-found"))
                .andExpect(model().attributeExists(RESOURCE_NAME, FIELD_NAME, FIELD_VALUE));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testRemovingRole() throws Exception {
        mockMvc.perform(patch("/users/roles/remove/{id}", testWorker.getId()).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/change/" + testWorker.getId().toString()));

    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testAddRole() throws Exception {
        mockMvc.perform(patch("/users/roles/add/{id}", testUser.getId()
                ).with(csrf()).param("id", testUser.getId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/change/" + testUser.getId().toString()));

    }

    @Test
    @WithMockUser(username = "workerUser@worker.com", roles = {"WORKER", "USER"})
    void testEditedUser() throws Exception {
        mockMvc.perform(patch("/users/edited/{id}", testUser.getId())
                        .with(csrf())
                        .param("firstName", "Daniel")
                        .param("lastName", "Baykov"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/profile"));
    }

    @Test
    @WithMockUser(username = "adminUserControl@admin.com", roles = "ADMIN")
    void testEditedUser_InvalidData() throws Exception {
        mockMvc.perform(patch("/users/edited/{id}", testUser.getId())
                        .with(csrf())
                        .param("firstName", "")
                        .param("lastName", "Baykov"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/edit/" + testUser.getId()));
    }
}
