package be.kdg.sa.warehouse.controller;


import be.kdg.sa.warehouse.controller.dto.PurchaseOrderRequestDto;
import be.kdg.sa.warehouse.service.PurchaseOrderSenderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
public class SenderControlller {


    private PurchaseOrderSenderService purchaseOrderSenderService;

    public SenderControlller(PurchaseOrderSenderService purchaseOrderSenderService) {
        this.purchaseOrderSenderService = purchaseOrderSenderService;
    }

    @PostMapping("/sendPurchaseOrder")
    public void sendPurchaseOrder(@RequestBody PurchaseOrderRequestDto requestDto) {
        purchaseOrderSenderService.sendPurchaseOrderToWaterApp(requestDto.getPurchaseOrderNumber(), requestDto.getVesselNumber());
    }
}
