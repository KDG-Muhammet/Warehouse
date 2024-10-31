package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.domain.Buyer;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.service.invoice.InvoiceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PurchaseOrderServiceTest {

    @Autowired
    private InvoiceService invoiceService;


    @Test
    public void calculateCommissionShouldReturnCorrectCommissionPrice() {

        // Arrange
        Material ironOre = new Material(MaterialType.IJZERERTS, "String description", new BigDecimal(30), new BigDecimal(50));
        Material gypsum = new Material(MaterialType.GIPS, "String description", new BigDecimal(100), new BigDecimal(50));
        Seller seller = new Seller("test", "ad");
        Buyer buyer = new Buyer("test", "ad");
        PurchaseOrder purchaseOrder = new PurchaseOrder("test1234", new Date(), seller, buyer, new ArrayList<>());
        OrderLine ironOreOrderLine = new OrderLine(100, ironOre);
        OrderLine gypsumOrderLine = new OrderLine(50, gypsum);
        purchaseOrder.getOrderLines().add(ironOreOrderLine);
        purchaseOrder.getOrderLines().add(gypsumOrderLine);


        // Act
        BigDecimal actualCommission = invoiceService.calculateCommission(purchaseOrder);
        BigDecimal expectedCommission = BigDecimal.valueOf((50 * 100_000 + 50 * 50_000) * 0.01).setScale(2, RoundingMode.HALF_UP);


        // Assert
        assertEquals(expectedCommission, actualCommission, "De berekende commissie is niet correct");


    }

    @Test
    public void calculateCommissionPriceShouldReturnZeroForNoOrderLines() {

        // Arrange
        Seller seller = new Seller("test", "ad");
        Buyer buyer = new Buyer("test", "ad");
        PurchaseOrder purchaseOrder = new PurchaseOrder("test1234", new Date(), seller, buyer, new ArrayList<>());


        // Act
        BigDecimal actualCommission = invoiceService.calculateCommission(purchaseOrder);
        BigDecimal expectedCommission = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);


        // Assert
        assertEquals(expectedCommission, actualCommission, "De berekende commissie is niet correct");


    }
}