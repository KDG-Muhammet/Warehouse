package be.kdg.sa.warehouse.service.invoice;

import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.domain.Delivery;
import be.kdg.sa.warehouse.service.delivery.DeliveryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InvoiceServiceTest {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private InvoiceService invoiceService;

    @Test
    void calculateDailyStorageCosts() {
        // Arrange
        invoiceService.calculateStorageCostsAndGenerateInvoices();
        LocalDateTime time1 = LocalDateTime.of(2024, 7, 27, 10, 0, 0);
        LocalDateTime time2 = LocalDateTime.of(2024, 7, 27, 9, 0, 0);
        LocalDateTime time3 = LocalDateTime.of(2024, 7, 27, 9, 10, 0);

        // Act
        Delivery del1 = deliveryService.findDeliveryByDeliveryDate(time1);
        Delivery del2 = deliveryService.findDeliveryByDeliveryDate(time2);
        Delivery del3  = deliveryService.findDeliveryByDeliveryDate(time3);
        BigDecimal totalStorageCost = del1.getAmount().multiply(new BigDecimal(del1.getDays()).multiply(del1.getStoragePrice()))
                .add(del2.getAmount().multiply(new BigDecimal(del2.getDays()).multiply(del2.getStoragePrice()))
                .add(del3.getAmount().multiply(new BigDecimal(del3.getDays()).multiply(del3.getStoragePrice()))));
        InvoiceDto invoice = invoiceService.findInvoiceBySellerName("seller");


        // Assert
        assertEquals(totalStorageCost.setScale(2, RoundingMode.HALF_UP), invoice.getTotalStorageCost());
    }
}