package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.OrderLineDto;
import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.po.PurchaseOrderRepository;
import be.kdg.sa.warehouse.service.warehouse.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final OrderLineService orderLineService;
    private final WarehouseService warehouseService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, OrderLineService orderLineService, WarehouseService warehouseService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.orderLineService = orderLineService;
        this.warehouseService = warehouseService;
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
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findPurchaseOrderByPoNumberAndVesselNumber(purchaseOrderDto.getPoNumber(), purchaseOrderDto.getVesselNumber());
        purchaseOrder.setStatus(Status.COMPLETED);
        logger.info("purchase order fulfill : {} ", purchaseOrder);
        updateWarehouse(purchaseOrder);
        calculateCommission(purchaseOrder);
    }

    @Transactional
    public void updateWarehouse(PurchaseOrder purchaseOrder) {
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            Optional<Warehouse> warehouseOptional = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(purchaseOrder.getSeller().getUUID(), orderLine.getMaterial().getId());
            Warehouse warehouse = warehouseOptional.get();
            int amount = orderLine.getAmount();
            BigDecimal availableStock = warehouse.getOccupancy();
            warehouse.setOccupancy(availableStock.subtract(BigDecimal.valueOf(amount)));
            //warehouseService.save(warehouse);
            logger.info("lower warehouse Occupancy by  : {} ", amount);
        }
    }


    public void calculateCommission(PurchaseOrder purchaseOrder) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            Material material = orderLine.getMaterial();
            BigDecimal pricePerTon = material.getSellingPrice();
            BigDecimal amountInTons = BigDecimal.valueOf(orderLine.getAmount() * 1000L);
            BigDecimal materialCost = pricePerTon.multiply(amountInTons);
            totalPrice = totalPrice.add(materialCost);
        }
        BigDecimal commissionPercentage = BigDecimal.valueOf(0.01); // 1% commissie
        totalPrice.multiply(commissionPercentage);
        logger.info("calculated commision : {} ", totalPrice);


    }


        public PurchaseOrder create(PurchaseOrder purchaseOrder) {
        logger.info("Creating po: {} ", purchaseOrder);
        return purchaseOrderRepository.save(purchaseOrder);
    }
}
