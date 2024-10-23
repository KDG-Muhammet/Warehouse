package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.service.po.PurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchaseOrder")
public class ReceiveRestController {

    private final PurchaseOrderService purchaseOrderService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public ReceiveRestController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping("")
    public void receivePurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        logger.info("Receiving PurchaseOrderDto: " + purchaseOrderDto.getVesselNumber());
        purchaseOrderService.updateOrder(purchaseOrderDto);

    }

}
