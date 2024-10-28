package be.kdg.sa.warehouse.service.invoice;

import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.InvoiceRepository;
import be.kdg.sa.warehouse.service.delivery.DeliveryService;
import be.kdg.sa.warehouse.service.seller.SellerService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

import static be.kdg.sa.warehouse.util.Calculation.calculateStoragePricePerDelivery;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final DeliveryService deliveryService;
    private final SellerService sellerService;

    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

    @Value("${convertToTon}")
    private long convertToTon;

    @Value("${commision}")
    private double commision;

    public InvoiceService(InvoiceRepository invoiceRepository, DeliveryService deliveryService, ModelMapper modelMapper, SellerService sellerService) {
        this.invoiceRepository = invoiceRepository;
        this.deliveryService = deliveryService;
        this.modelMapper = modelMapper;
        this.sellerService = sellerService;
    }
    public void updateCommission(BigDecimal commission, Seller seller) {
        Invoice invoice = invoiceRepository.findInvoiceBySeller(seller);
        BigDecimal totalCommision = invoice.getTotalCommisionCost();
        invoice.setTotalCommisionCost(totalCommision.add(commission));
    }

    @Transactional
    public void updateTotalStorageCost() {
        Collection<Seller> sellers = sellerService.findAllSellers();
        for (Seller seller : sellers) {
            BigDecimal totalStorageCost = BigDecimal.ZERO;
            Invoice invoice = invoiceRepository.findInvoiceBySeller(seller);

            Collection<Warehouse> warehouses = seller.getWarehouses();
            for (Warehouse warhouse : warehouses) {
                Collection<Delivery> deliveries = warhouse.getDeliveries();
                for (Delivery delivery : deliveries) {
                    totalStorageCost = totalStorageCost.add(delivery.getCostPrice());
                }
            }
            invoice.setTotalStorageCost(totalStorageCost);
            logger.info("Updated total storage cost for seller {}: {}", seller.getName(), totalStorageCost);
        }
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

    @Transactional
    @Scheduled(cron = "0 0 9 * * ?")
    public void calculateStorageCostsAndGenerateInvoices() {
        try {
            logger.info("Calculating storage costs and generating invoices at 9:00 AM");
            calculateDailyStorageCosts();
            updateTotalStorageCost();
            logger.info("Storage cost calculation and invoice generation completed successfully.");
        } catch (Exception e) {
            logger.error("Failed to calculate storage costs and generate invoices", e);
        }
    }

    @Transactional
    public void calculateDailyStorageCosts() {
        Collection<Delivery> deliveries = deliveryService.findAllDeliveries();
        for (Delivery delivery : deliveries) {
            long days = ChronoUnit.DAYS.between(delivery.getDeliveryDate().toLocalDate(), LocalDateTime.now().toLocalDate());
            days = Math.max(days, 1);
            BigDecimal totalDeliveryCost = calculateStoragePricePerDelivery((int) days, delivery.getStoragePrice(), delivery.getAmount());
            delivery.setDays((int) days);
            delivery.setCostPrice(totalDeliveryCost);
            logger.info("Opslagkosten voor levering {} berekend: {} dollar voor {} dagen",
                    delivery.getUuid(), delivery.getCostPrice(), days);
        }
    }

    @Transactional
    public List<InvoiceDto> findAllInvoices() {
        return invoiceRepository.findAll().stream().map(m -> modelMapper.map(m, InvoiceDto.class)).toList();
    }

    @Transactional(readOnly = true)
    public InvoiceDto findInvoiceBySellerName(String sellerName) {
        Invoice invoice = invoiceRepository.findInvoiceBySellerName(sellerName);
        return modelMapper.map(invoice, InvoiceDto.class);
    }

}