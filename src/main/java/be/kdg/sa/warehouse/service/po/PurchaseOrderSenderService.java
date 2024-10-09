package be.kdg.sa.warehouse.service.po;

import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PurchaseOrderSenderService {

    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);
    private static final String WATER_APP_URL = "http://localhost:8080/api/purchaseOrders";

    public PurchaseOrderSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<String> sendPurchaseOrderToWaterApp(String poNumber, String vesselNumber ){
        PurchaseOrderRequestDto requestDto = new PurchaseOrderRequestDto(poNumber, vesselNumber);
        logger.info("sending po: " + "ponumber: " + poNumber +  " vesselNumber: " +vesselNumber);

       return restTemplate.postForEntity(WATER_APP_URL, requestDto, String.class);

    }



}
