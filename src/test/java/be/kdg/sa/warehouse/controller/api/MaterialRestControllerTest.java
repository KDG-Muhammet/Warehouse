package be.kdg.sa.warehouse.controller.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class MaterialRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void fetchAllExistingMaterialsShouldReturnLength5() throws Exception {
        mockMvc.perform(get("/api/materials").accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void updatingExistingMaterialShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/materials")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content("""
                                {
                                "materialType": "PETCOKE",
                                  "sellingPrice": "20",
                                  "storagePrice": "500"
                                }
                                """)
                )
                .andExpect(status().isOk());
    }

    @Test
    void updatingNonExistingMaterialShouldReturnNotFound() throws Exception {
        mockMvc.perform(put("/api/materials")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content("""
                                {
                                "materialType": "",
                                  "sellingPrice": "20",
                                  "storagePrice": "500"
                                }
                                """)
                )
                .andExpect(status().isBadRequest());
    }
}