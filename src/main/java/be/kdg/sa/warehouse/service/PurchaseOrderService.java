package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.OrderLineDto;
import be.kdg.sa.warehouse.controller.dto.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.*;
import be.kdg.sa.warehouse.repository.BuyerRepository;
import be.kdg.sa.warehouse.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PurchaseOrderService {

    private PurchaseOrderRepository purchaseOrderRepository;
    private BuyerService buyerService;
    private SellerService sellerService;
    private OrderLineService orderLineService;
    private MaterialService materialService;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, BuyerService buyerService, SellerService sellerService, OrderLineService orderLineService, MaterialService materialService) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.buyerService = buyerService;
        this.sellerService = sellerService;
        this.orderLineService = orderLineService;
        this.materialService = materialService;
    }

    @Transactional(readOnly = true)
    public Collection<PurchaseOrderDto> getAllOrders() {

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
    public PurchaseOrderDto getPurchaseOrderByPoNumber(String poNumber) {

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
    public PurchaseOrder makeOrder(PurchaseOrderDto purchaseOrderDto) {
        Buyer buyer = buyerService.findBuyerByNameAndAddress(purchaseOrderDto.getBuyerName(), purchaseOrderDto.getBuyerAddress());
        Seller seller = sellerService.findSellerByNameAndAddress(purchaseOrderDto.getSellerName(), purchaseOrderDto.getSellerAddress());
        // Maak de nieuwe PurchaseOrder aan
        PurchaseOrder purchaseOrder = new PurchaseOrder(purchaseOrderDto.getPoNumber(), purchaseOrderDto.getDate(), purchaseOrderDto.getVesselNumber(), seller, buyer, new ArrayList<>());
        // Stel voor elke OrderLine de PurchaseOrder in
        for (OrderLineDto orderLineDto : purchaseOrderDto.getOrderLines()) {

            OrderLine orderLine = new OrderLine();

            logger.info("incoming material:" + orderLineDto.getAmount() + orderLineDto.getMaterialName());
            Material material = materialService.findMaterialByType(orderLineDto.getMaterialName());
            orderLine.setAmount(orderLineDto.getAmount());
            orderLine.setMaterial(material);

            orderLine.setPurchaseOrder(purchaseOrder);
            purchaseOrder.getOrderLines().add(orderLine);
        }

        return purchaseOrderRepository.save(purchaseOrder);
    }
}
