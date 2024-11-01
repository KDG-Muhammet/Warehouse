package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Test
    void shouldReturnAllInvoices() throws Exception {
        mockMvc.perform(get("/api/invoices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].seller.sellerName").value("seller"))
                .andExpect(jsonPath("$[0].seller.sellerAddress").value("test address seller"))
                .andExpect(jsonPath("$[0].totalCommisionCost").value(0.00))
                .andExpect(jsonPath("$[0].totalStorageCost").isEmpty());
    }

    @Test
    void GetInvoiceShouldReturnInvoiceOfSeller() throws Exception {
        String sellerName = "seller";

        mockMvc.perform(get("/api/invoices/{seller}", sellerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seller.sellerName").value(sellerName))
                .andExpect(jsonPath("$.seller.sellerAddress").value("test address seller"))
                .andExpect(jsonPath("$.totalCommisionCost").value(0.00));
    }

    @Test
    void GetInvoiceShouldReturnNotFoundWhenSellerNotExists() throws Exception {
        String sellerName = "unknown";

        mockMvc.perform(get("/api/invoices/{seller}", sellerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void updateInvoices_ShouldCalculateAndSetTotalStorageCost() throws Exception {
        String sellerName = "seller";
        Invoice existingInvoice = invoiceRepository.findInvoiceBySellerName(sellerName);

        assertNull(existingInvoice.getTotalStorageCost());

        mockMvc.perform(put("/api/invoices"))
                .andExpect(status().isNoContent());

        Invoice updatedInvoice = invoiceRepository.findInvoiceBySellerName(sellerName);

        assertNotNull(updatedInvoice.getTotalStorageCost());
        assertTrue(updatedInvoice.getTotalStorageCost().compareTo(BigDecimal.ZERO) > 0);
    }


}