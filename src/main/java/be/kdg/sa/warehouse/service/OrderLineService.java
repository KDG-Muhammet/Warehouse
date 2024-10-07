package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.domain.OrderLine;
import be.kdg.sa.warehouse.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class OrderLineService {

    private OrderLineRepository orderLineRepository;

    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    public Collection<OrderLine> findOrderLinessWithPurchaseOrders() {
        return orderLineRepository.findAll();

    }

    public Collection<OrderLine> findOrderLinessbyPurchaseOrderId(UUID referenceUUID) {
        return orderLineRepository.getOrderLinesByPurchaseOrderReferenceUUID(referenceUUID);
    }
}
