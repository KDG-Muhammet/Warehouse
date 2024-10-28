package be.kdg.sa.warehouse.repository.po;

import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {
    PurchaseOrder findPurchaseOrderByPoNumber(String poNumber);

    List<PurchaseOrder> findAllByStatus(Status status);
}
