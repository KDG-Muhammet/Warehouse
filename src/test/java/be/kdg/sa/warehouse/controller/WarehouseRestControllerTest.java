package be.kdg.sa.warehouse.controller;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class WarehouseRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void findAllWarehouses() throws Exception {
        mockMvc.perform(get("/api/warehouses"))
                .andExpect(status().isOk());
    }

    @Test
    void findWarehouseId(){
        ServletException exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/warehouses///"))
                    .andExpect(status().isBadRequest());
        });

        // Controleer of de oorzaak een IllegalArgumentException is
        assertInstanceOf(IllegalArgumentException.class, exception.getCause());
    }

    @Test
    void findWarehouseById() throws Exception {
        mockMvc.perform(get("/api/warehouses/")).andExpect(status().isNotFound());
    }
}