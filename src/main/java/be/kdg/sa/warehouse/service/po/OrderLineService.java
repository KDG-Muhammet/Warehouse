package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.repository.po.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public Collection<OrderLine> findOrderLinesByPurchaseOrderId(UUID referenceUUID) {
        return orderLineRepository.getOrderLinesByPurchaseOrderReferenceUUID(referenceUUID);
    }
}
