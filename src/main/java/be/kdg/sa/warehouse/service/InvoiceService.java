package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Value("${convertToTon}")
    private long convertToTon;

    @Value("${commision}")
    private double commision;
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void updateCommission(BigDecimal commission, Seller seller) {
       Invoice invoice = invoiceRepository.findInvoiceBySeller(seller);
       BigDecimal totalCommision = invoice.getTotalCommisionCost();
       invoice.setTotalCommisionCost(totalCommision.add(commission));
    }

    public BigDecimal calculateCommission(PurchaseOrder purchaseOrder) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            Material material = orderLine.getMaterial();

            BigDecimal pricePerTon = material.getSellingPrice();
            BigDecimal amountInTons = BigDecimal.valueOf(orderLine.getAmount() * convertToTon);
            BigDecimal materialCost = pricePerTon.multiply(amountInTons);
            totalPrice = totalPrice.add(materialCost);
        }
        BigDecimal commissionPercentage = BigDecimal.valueOf(commision); // 1% commissie
        totalPrice = totalPrice.multiply(commissionPercentage);
        logger.info("   calculated commision : {} ", totalPrice);
        return totalPrice;
    }

}