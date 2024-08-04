package com.kth.project_dollarstore.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kth.project_dollarstore.model.Customer;
import com.kth.project_dollarstore.model.DeleteReason;
import com.kth.project_dollarstore.model.Reason;
import com.kth.project_dollarstore.service.CustomerService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    @Test
    public void http_Post_RegisterCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Joar Gerlov");
        customer.setEmail("gerlov@kth.se");
        customer.setPassword("Password123");
        customer.setDob(LocalDate.of(1996, 4, 15));

        mockMvc.perform(post("/api/v1/customers/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(customer)))
        .andExpect(status().isOk());
    }

    @Test
    public void http_Get_GetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setName("Joar Gerlov");
        customer.setEmail("gerlov@kth.se");
        customer.setPassword("passworD123");
        customer.setDob(LocalDate.of(1996, 4, 15));

        String result = customerService.addCustomer(customer);
        assert result.equals("Customer registered successfully");

        Optional<Customer> savedCustomer = customerService.getCustomerByEmail("gerlov@kth.se");

        mockMvc.perform(get("/api/v1/customers/{id}", savedCustomer.get().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Joar Gerlov"))
                .andExpect(jsonPath("$.email").value("gerlov@kth.se"));
    }

    @Test
    public void http_Delete_DeleteCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setName("Joar Gerlov");
        customer.setEmail("gerlv@kth.se");
        customer.setPassword("passworD123");
        customer.setDob(LocalDate.of(1996, 4, 15));

        String result = customerService.addCustomer(customer);
        assert result.equals("Customer registered successfully");

        Optional<Customer> savedCustomer = customerService.getCustomerByEmail("gerlv@kth.se");

        mockMvc.perform(delete("/api/v1/customers/{id}", savedCustomer.get().getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void http_Put_UpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Joar Gerlov");
        customer.setEmail("ov@kth.se");
        customer.setPassword("passworD123");
        customer.setDob(LocalDate.of(1996, 4, 15));

        String result = customerService.addCustomer(customer);
        assert result.equals("Customer registered successfully");

        Optional<Customer> savedCustomer = customerService.getCustomerByEmail("ov@kth.se");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setName("Julia Zubko");

        mockMvc.perform(put("/api/v1/customers/{id}", savedCustomer.get().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Julia Zubko"));
    }

    @Test
    public void shouldLoginCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Joar Gerlov");
        customer.setEmail("g@kth.se");
        customer.setPassword("passworD123");
        customer.setDob(LocalDate.of(1996, 4, 15));

        String result = customerService.addCustomer(customer);
        assert result.equals("Customer registered successfully");

        Customer loginDetails = new Customer();
        loginDetails.setEmail("g@kth.se");
        loginDetails.setPassword("passworD123");

        Optional<Customer> savedCustomer = customerService.getCustomerByEmail("g@kth.se");

        mockMvc.perform(post("/api/v1/customers/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(savedCustomer.get().getId())));
    }

    @Test
    public void http_Put_ChangePassword_And_Login() throws Exception {
        Customer customer = new Customer();
        customer.setName("Cristian Poblete");
        customer.setEmail("cj@kth.se");
        customer.setPassword("passworD123");
        customer.setDob(LocalDate.of(1996, 4, 15));
    
        String result = customerService.addCustomer(customer);
        assert result.equals("Customer registered successfully");
    
        Optional<Customer> savedCustomer = customerService.getCustomerByEmail("cj@kth.se");
        assert savedCustomer.isPresent();
        
        Customer updatedCustomer = new Customer();
        updatedCustomer.setPassword("Password123");
    
        mockMvc.perform(put("/api/v1/customers/{customerId}", savedCustomer.get().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk());
    
        Customer loginDetails = new Customer();
        loginDetails.setEmail("cj@kth.se");
        loginDetails.setPassword("Password123");
    
        mockMvc.perform(post("/api/v1/customers/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(savedCustomer.get().getId())));
    }
    

    @Test // Testa svag lösen
    public void http_Post_RegisterCustomer_WithWeakPassword() throws Exception {
        Customer customer = new Customer();
        customer.setName("Cristian Poblete");
        customer.setEmail("cjbp@kth.se");
        customer.setPassword("123");
        customer.setDob(LocalDate.of(1996, 4, 15));
    
        mockMvc.perform(post("/api/v1/customers/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isBadRequest())  // 400 "Weak password"
                .andExpect(content().string(""));
    }

    @Test // Testa posta en delete reason
    public void shouldAddDeleteReasonWithEnum() throws Exception {
        DeleteReason deleteReason = new DeleteReason();
        deleteReason.setReason(Reason.NOT_FOUND_PRODUCTS);
        deleteReason.setCreatedAt(LocalDateTime.now());
        deleteReason.setOtherReason(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers/delete-reason")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteReason)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reason").value("NOT_FOUND_PRODUCTS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.otherReason").doesNotExist());
    }

}
