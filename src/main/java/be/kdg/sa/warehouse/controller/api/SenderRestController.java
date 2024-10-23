package be.kdg.sa.warehouse.controller.api;


import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderRequestDto;
import be.kdg.sa.warehouse.service.po.PurchaseOrderSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
public class SenderRestController {


    private final PurchaseOrderSenderService purchaseOrderSenderService;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderSenderService.class);

    public SenderRestController(PurchaseOrderSenderService purchaseOrderSenderService) {
        this.purchaseOrderSenderService = purchaseOrderSenderService;
    }

    @PostMapping("/sendPurchaseOrder")
    public ResponseEntity<String> sendPurchaseOrder(@RequestBody PurchaseOrderRequestDto requestDto) {
        logger.info("Receiving PurchaseOrderDto: " + requestDto.getPoNumber());
        return purchaseOrderSenderService.sendPurchaseOrderToWaterApp(requestDto.getPoNumber(), requestDto.getVesselNumber());
    }
}
