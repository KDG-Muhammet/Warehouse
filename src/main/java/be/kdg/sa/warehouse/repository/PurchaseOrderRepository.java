package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, UUID> {


    PurchaseOrder findPurchaseOrderByPoNumber(String poNumber);
}
