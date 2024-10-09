package be.kdg.sa.warehouse.repository.po;

import be.kdg.sa.warehouse.domain.po.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {


    Collection<OrderLine> getOrderLinesByPurchaseOrderReferenceUUID(UUID uuid);


}
