package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.service.po.PurchaseOrderService;
import be.kdg.sa.warehouse.service.po.UpdatePurchaseOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchaseOrder")
public class ReceiveRestController {

    private final UpdatePurchaseOrderService updatePurchaseOrderService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    public ReceiveRestController(UpdatePurchaseOrderService updatePurchaseOrderService) {
        this.updatePurchaseOrderService = updatePurchaseOrderService;
    }


    @PostMapping("/purchaseOrder")
    public void receivePurchaseOrder(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        logger.info("Receiving PurchaseOrder: " + purchaseOrderDto.getPoNumber());
        updatePurchaseOrderService.updateOrder(purchaseOrderDto);
    }

    @PostMapping("/shippingOrder")
    public void receiveShippingOrder(@RequestBody PurchaseOrderDto purchaseOrderDto) {
        logger.info("Receiving ShippingOrder with poNumber: " + purchaseOrderDto.getPoNumber());
        updatePurchaseOrderService.expectingOrder(purchaseOrderDto);
    }

}
