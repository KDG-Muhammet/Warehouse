package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.service.po.CreatePurchaseOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@SpringBootTest
@AutoConfigureMockMvc
class PurchaseOrderRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreatePurchaseOrderService createPurchaseOrderService;

    @Test
    public void addingANewPoShouldReturnCreated() throws Exception {
        mockMvc.perform(post("/api/purchaseOrders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                   "poNumber": "testpoNumber",
                                   "buyerName": "buyer",
                                   "buyerAddress": "test address buyer",
                                   "sellerName": "seller",
                                   "sellerAddress": "test address seller",
                                   "date": "2024-09-29T00:00:00",
                                   "orderLines": [
                                     {
                                       "amount": 50,
                                       "materialName": "GIPS"
                                     },
                                     {
                                       "amount": 0.01,
                                       "materialName": "IJZERERTS"
                                     }
                                   ]
                                 }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().doesNotExist(CONTENT_TYPE));

        verify(createPurchaseOrderService).createPurchaseOrder(any(PurchaseOrderDto.class));

    }
    @Test
    public void addingAnInvalidPOShouldFailWithBadRequest() throws Exception {

        mockMvc.perform(post("/api/purchaseOrders")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                         "buyerName": ""
                                        }
                                        """
                        ))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void GetAllPurchaseOrdersShouldReturnPos() throws Exception {

        mockMvc.perform(get("/api/purchaseOrders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].poNumber").value("PO12345"));

    }

    @Test
    public void GetPurchaseShouldReturnExistingPo() throws Exception {

        String poNumber = "PO12345";

        mockMvc.perform(get("/api/purchaseOrders/{poNumber}", poNumber))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.poNumber").value(poNumber));
    }

    @Test
    public void getPurchaseOrderShouldReturnNotFoundWhenPoIsNull() throws Exception {
        String poNumber = "PO1234";

        mockMvc.perform(get("/api/purchaseOrders/{poNumber}", poNumber))
                .andExpect(status().isNotFound());
    }

}