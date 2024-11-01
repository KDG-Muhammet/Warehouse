package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.service.invoice.InvoiceService;
import be.kdg.sa.warehouse.service.material.MaterialService;
import be.kdg.sa.warehouse.service.po.PurchaseOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReceiveRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private MaterialService materialService;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    public void receiveShippingOrderShouldUpdateStatusToExpecting() throws Exception {
        String poNumber = "PO12345";

        mockMvc.perform(post("/api/purchaseOrder/shippingOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "poNumber": "PO12345"
                                }
                                """))
                .andExpect(status().isOk());

        // Assert: Haal de PurchaseOrder op en controleer de status
        PurchaseOrder updatedOrder = purchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
        assertEquals(Status.EXPECTING, updatedOrder.getStatus(), "De status zou EXPECTING moeten zijn");
    }

    @Test
    public void receivePurchaseOrderShouldPutOnHoldWhenInsufficientStock() throws Exception {
        String poNumber = "PO12345";
        PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
        Material material = materialService.findMaterialByType("IJZERERTS");
        OrderLine orderLine = new OrderLine(1,material );
        purchaseOrder.getOrderLines().add(orderLine);

        mockMvc.perform(post("/api/purchaseOrder/purchaseOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "poNumber": "PO12345"
                                }
                                """))
                .andExpect(status().isOk());

        // Assert: Haal de PurchaseOrder op en controleer de status
        assertEquals(Status.ON_HOLD, purchaseOrder.getStatus(), "De status zou ON_HOLD moeten zijn door onvoldoende voorraad");
    }

    @Test
    public void receivePoShouldCompleteWhenSufficientStockAndUpdateCommission() throws Exception {
        String poNumber = "PO12345";
        PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
        Material material = materialService.findMaterialByType("IJZERERTS");
        OrderLine orderLine = new OrderLine(0.1,material );
        purchaseOrder.getOrderLines().add(orderLine);

        mockMvc.perform(post("/api/purchaseOrder/purchaseOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "poNumber": "PO12345"
                                }
                                """))
                .andExpect(status().isOk());

        // Assert: Controleer of de PurchaseOrder is voltooid
        assertEquals(Status.COMPLETED, purchaseOrder.getStatus(), "De status zou COMPLETED moeten zijn bij voldoende voorraad");

        // Controleer of de commissie is bijgewerkt
        BigDecimal calculatedCommission = invoiceService.calculateCommission(purchaseOrder);
        InvoiceDto invoiceDto = invoiceService.findInvoiceBySellerName(purchaseOrder.getSeller().getName());
        assertEquals(calculatedCommission, invoiceDto.getTotalCommisionCost(), "De calculatedCommission zou gelijk moeten zijn aan commissin invoice ");
    }

}