package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.OrderLineDto;
import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GetPurchaseOrderService {

    private final PurchaseOrderService purchaseOrderService;
    private final OrderLineService orderLineService;
    public GetPurchaseOrderService(PurchaseOrderService purchaseOrderService, OrderLineService orderLineService) {
        this.purchaseOrderService = purchaseOrderService;
        this.orderLineService = orderLineService;
    }

    @Transactional(readOnly = true)
    public Collection<PurchaseOrderDto> findAllOrders() {
        Collection<PurchaseOrder> purchaseOrders = purchaseOrderService.findAll();
        List<PurchaseOrderDto> purchaseOrderDtos = new ArrayList<>();

        purchaseOrders.forEach(purchaseOrder -> {
            PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(purchaseOrder);
            Collection<OrderLine> orderLineCollection = orderLineService.findOrderLinessbyPurchaseOrderId(purchaseOrder.getReferenceUUID());
            List<OrderLineDto> orderLineDtoArrayList = new ArrayList<>();
            orderLineCollection.forEach(orderLine -> {
                OrderLineDto orderLineDto = new OrderLineDto(orderLine);
                orderLineDtoArrayList.add(orderLineDto);
            });
            purchaseOrderDto.setOrderLines(orderLineDtoArrayList);
            purchaseOrderDtos.add(purchaseOrderDto);
        });
        return purchaseOrderDtos;
    }

    @Transactional(readOnly = true)
    public PurchaseOrderDto findPurchaseOrderByPoNumber(String poNumber) {
        PurchaseOrder purchaseOrder = purchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(purchaseOrder);
        List<OrderLineDto> orderLineDtos = new ArrayList<>();
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            OrderLineDto orderLineDto = new OrderLineDto(orderLine);
            orderLineDtos.add(orderLineDto);
        }
        purchaseOrderDto.setOrderLines(orderLineDtos);
        return purchaseOrderDto;
    }
}
