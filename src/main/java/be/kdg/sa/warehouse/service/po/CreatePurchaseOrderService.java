package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.Buyer;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.enums.Status;
import be.kdg.sa.warehouse.domain.po.OrderLine;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.service.BuyerService;
import be.kdg.sa.warehouse.service.seller.SellerService;
import be.kdg.sa.warehouse.service.material.MaterialService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;

@Service
public class CreatePurchaseOrderService {

    private final BuyerService buyerService;
    private final SellerService sellerService;
    private final MaterialService materialService;
    private final PurchaseOrderService purchaseOrderService;
    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(CreatePurchaseOrderService.class);

    public CreatePurchaseOrderService(BuyerService buyerService, SellerService sellerService, MaterialService materialService, PurchaseOrderService purchaseOrderService, ModelMapper modelMapper) {
        this.buyerService = buyerService;
        this.sellerService = sellerService;
        this.materialService = materialService;
        this.purchaseOrderService = purchaseOrderService;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public void createPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
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
        purchaseOrderService.create(purchaseOrder);
    }
}
