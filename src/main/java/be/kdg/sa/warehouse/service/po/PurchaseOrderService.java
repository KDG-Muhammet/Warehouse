package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.OrderLineDto;
import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.repository.po.PurchaseOrderRepository;
import be.kdg.sa.warehouse.service.BuyerService;
import be.kdg.sa.warehouse.service.material.MaterialService;
import be.kdg.sa.warehouse.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final BuyerService buyerService;
    private final SellerService sellerService;
    private final OrderLineService orderLineService;
    private final MaterialService materialService;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BuyerService buyerService, SellerService sellerService, OrderLineService orderLineService, MaterialService materialService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.buyerService = buyerService;
        this.sellerService = sellerService;
        this.orderLineService = orderLineService;
        this.materialService = materialService;
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
        // Convert to PurchaseOrderDto
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto(purchaseOrder);
        // Convert OrderLines to DTOs
        List<OrderLineDto> orderLineDtos = new ArrayList<>();
        for (OrderLine orderLine : purchaseOrder.getOrderLines()) {
            OrderLineDto orderLineDto = new OrderLineDto(orderLine);
            orderLineDtos.add(orderLineDto);
        }

        purchaseOrderDto.setOrderLines(orderLineDtos);

        return purchaseOrderDto;


    }

    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        Buyer buyer = buyerService.findBuyerByNameAndAddress(purchaseOrderDto.getBuyerName(), purchaseOrderDto.getBuyerAddress());
        Seller seller = sellerService.findSellerByNameAndAddress(purchaseOrderDto.getSellerName(), purchaseOrderDto.getSellerAddress());
        // Maak de nieuwe PurchaseOrder aan
        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderDto.getPoNumber(), purchaseOrderDto.getDate(), purchaseOrderDto.getVesselNumber(), seller, buyer, new ArrayList<>());
        purchaseOrder.setStatus(Status.ONGOING);
        // Stel voor elke OrderLine de PurchaseOrder in

        purchaseOrderDto.getOrderLines().forEach(orderLineDto -> {
            logger.info("incoming material:" + orderLineDto.getAmount() + orderLineDto.getMaterialName());
            Material material = materialService.findMaterialByType(orderLineDto.getMaterialName());

            OrderLine orderLine = new OrderLine(orderLineDto.getAmount(), material);
            orderLine.setPurchaseOrder(purchaseOrder);

            purchaseOrder.getOrderLines().add(orderLine);
        });

        return purchaseOrderRepository.save(purchaseOrder);
    }
}
