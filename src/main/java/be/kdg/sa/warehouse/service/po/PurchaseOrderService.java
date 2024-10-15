package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.OrderLineDto;
import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.po.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderLineService orderLineService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,OrderLineService orderLineService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderLineService = orderLineService;
    }
    @Transactional(readOnly = true)
    public Collection<PurchaseOrderDto> findAllOrders() {
        Collection<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
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
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumber(poNumber);
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(purchaseOrder);
        List<OrderLineDto> orderLineDtos = new ArrayList<>();
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            OrderLineDto orderLineDto = new OrderLineDto(orderLine);
            orderLineDtos.add(orderLineDto);
        }
        purchaseOrderDto.setOrderLines(orderLineDtos);
        return purchaseOrderDto;
    }
    @Transactional
    public void updateOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumberAndVesselNumber(purchaseOrderDto.getPoNumber(),purchaseOrderDto.getVesselNumber());
        purchaseOrder.setStatus(Status.COMPLETED);
    }
    public PurchaseOrder create(PurchaseOrder purchaseOrder) {
        logger.info("Creating po: {} ", purchaseOrder);
       return purchaseOrderRepository.save(purchaseOrder);
    }
}
