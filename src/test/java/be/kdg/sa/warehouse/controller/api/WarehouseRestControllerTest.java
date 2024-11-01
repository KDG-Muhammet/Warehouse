package be.kdg.sa.warehouse.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class WarehouseRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchAllExistingWarehousesShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/warehouses").accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void fetchingAnExistingWarehouseShouldReturnWarehouseUUID() throws Exception {
        mockMvc.perform(get("/api/warehouses/PETCOKE/seller/Test seller address")
                        .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, APPLICATION_JSON.toString()));
    }

    @Test
    void fetchNonExistingWarehouseOccupancyShouldReturnIsNotFound() throws Exception {
        mockMvc.perform(get("/api/warehouses/").param("warehouseId", "")).andExpect(status().isNotFound());
    }
}