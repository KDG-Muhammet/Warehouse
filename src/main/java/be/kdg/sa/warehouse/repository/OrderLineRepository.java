package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {


    Collection<OrderLine> getOrderLinesByPurchaseOrderReferenceUUID(UUID uuid);


}
