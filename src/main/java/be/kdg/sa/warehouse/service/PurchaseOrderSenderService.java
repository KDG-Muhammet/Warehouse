package be.kdg.sa.warehouse.service;

import be.kdg.sa.warehouse.controller.dto.PurchaseOrderRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PurchaseOrderSenderService {

    private RestTemplate restTemplate;

    public PurchaseOrderSenderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static final String WATER_APP_URL = "http://localhost:8080/api/purchaseOrders"; // Replace with actual water app URL

    public void sendPurchaseOrderToWaterApp(String poNumber, String vesselNumber ){
        PurchaseOrderRequestDto requestDto = new PurchaseOrderRequestDto(poNumber, vesselNumber);

        restTemplate.postForEntity(WATER_APP_URL, requestDto, Void.class);

    }



}
