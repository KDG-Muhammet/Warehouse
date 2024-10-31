package be.kdg.sa.warehouse.service.po;


import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.po.PurchaseOrderRepository;
import be.kdg.sa.warehouse.service.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.util.*;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final WarehouseService warehouseService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, WarehouseService warehouseService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.warehouseService = warehouseService;
    }

    public Collection<PurchaseOrder> findAll() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findPurchaseOrderByPoNumber(String poNumber) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumber(poNumber);
        if (purchaseOrder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase Order not found");
        }
        return purchaseOrder;
    }

    public boolean isStockAvailable(PurchaseOrder purchaseOrder) {
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            BigDecimal amountInTon = BigDecimal.valueOf(orderLine.getAmount() * 1000L);
            Warehouse warehouse = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(
                    purchaseOrder.getSeller().getUUID(), orderLine.getMaterial().getId()
            ).orElse(null);

            if (warehouse == null || warehouse.getOccupancy().compareTo(amountInTon) < 0) {
                return false;
            }
        }
        return true;
    }

    public void create(PurchaseOrder purchaseOrder) {
        logger.info("Creating po: {} ", purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> findAllByStatus(Status status) {
       return purchaseOrderRepository.findAllByStatus(status);
    }
}
