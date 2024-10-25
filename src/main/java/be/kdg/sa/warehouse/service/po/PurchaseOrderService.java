package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.po.PurchaseOrderRepository;
import be.kdg.sa.warehouse.service.InvoiceService;
import be.kdg.sa.warehouse.service.warehouse.UpdateWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UpdateWarehouseService updateWarehouseService;
    private final InvoiceService invoiceService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, UpdateWarehouseService updateWarehouseService, InvoiceService invoiceService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.updateWarehouseService = updateWarehouseService;
        this.invoiceService = invoiceService;
    }

    public Collection<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findPurchaseOrderByPoNumber(String poNumber) {
        return purchaseOrderRepository.findPurchaseOrderByPoNumber(poNumber);
    }

    @Transactional
    public void updateOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumber(purchaseOrderDto.getPoNumber());
        purchaseOrder.setStatus(Status.COMPLETED);
        logger.info(    "purchase order fulfill : {} ", purchaseOrder);
        updateWarehouseService.updateWarehouse(purchaseOrder);
        BigDecimal commission = invoiceService.calculateCommission(purchaseOrder);
        invoiceService.updateCommission(commission, purchaseOrder.getSeller());
    }

    @Transactional
    public void expectingOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumber(purchaseOrderDto.getPoNumber());
        purchaseOrder.setStatus(Status.EXPECTING);

    }

    public void create(PurchaseOrder purchaseOrder) {
        logger.info(    "Creating po: {} ", purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder);
    }


}
