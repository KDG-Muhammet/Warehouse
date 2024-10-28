package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.service.InvoiceService;
import be.kdg.sa.warehouse.service.warehouse.UpdateWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UpdatePurchaseOrderService {

    private final PurchaseOrderService purchaseOrderService;
    private final UpdateWarehouseService updateWarehouseService;
    private final InvoiceService invoiceService;
    private static final Logger logger = LoggerFactory.getLogger(UpdatePurchaseOrderService.class);

    public UpdatePurchaseOrderService(PurchaseOrderService purchaseOrderService, UpdateWarehouseService updateWarehouseService, InvoiceService invoiceService) {
        this.purchaseOrderService = purchaseOrderService;
        this.updateWarehouseService = updateWarehouseService;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public void updateOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderByPoNumber(purchaseOrderDto.getPoNumber());
        if (!purchaseOrderService.isStockAvailable(purchaseOrder)) {
            purchaseOrder.setStatus(Status.ON_HOLD);
            logger.info("   Purchase order put on hold due to insufficient stock: {}", purchaseOrder.getPoNumber());
        } else {
            purchaseOrder.setStatus(Status.COMPLETED);
            logger.info("   purchase order fulfill : {} ", purchaseOrder);
            updateWarehouseService.updateWarehouse(purchaseOrder);
            BigDecimal commission = invoiceService.calculateCommission(purchaseOrder);
            invoiceService.updateCommission(commission, purchaseOrder.getSeller());
        }
    }

    @Transactional
    public void expectingOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderByPoNumber(purchaseOrderDto.getPoNumber());
        purchaseOrder.setStatus(Status.EXPECTING);

    }

    @Transactional
    public void checkPosOnHold() {
        List<PurchaseOrder> onHoldOrders = purchaseOrderService.findAllByStatus(Status.ON_HOLD);
        for (PurchaseOrder onHoldOrder : onHoldOrders) {
            if (purchaseOrderService.isStockAvailable(onHoldOrder)) {
                onHoldOrder.setStatus(Status.COMPLETED);
                logger.info("   Releasing on-hold purchase order: {}", onHoldOrder);
                updateWarehouseService.updateWarehouse(onHoldOrder);

                BigDecimal commission = invoiceService.calculateCommission(onHoldOrder);
                invoiceService.updateCommission(commission, onHoldOrder.getSeller());
            }
        }

    }
}
