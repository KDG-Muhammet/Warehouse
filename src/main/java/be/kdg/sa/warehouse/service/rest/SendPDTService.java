package be.kdg.sa.warehouse.service.rest;

import be.kdg.sa.warehouse.controller.dto.DeliveryDto;
import be.kdg.sa.warehouse.controller.dto.PdtDto;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class SendPDTService {
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(SendPDTService.class);

    @Value("${spring.application.land.url}")
    private String LAND_URL;

    public SendPDTService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Transactional
    public void sendPDT(DeliveryDto deliveryDto) {
        logger.info("   Sending PDT for delivery {}:{} to land", deliveryDto.getDeliveryTime().getHour(), deliveryDto.getDeliveryTime().getMinute());
        PdtDto pdtDto = modelToObject(deliveryDto);
        String fullUrl = LAND_URL + "/pdt";
        restTemplate.postForObject(fullUrl, pdtDto, void.class);
    }

    private PdtDto modelToObject(DeliveryDto deliveryDto) {
        MaterialType materialType = MaterialType.valueOf(deliveryDto.getMaterialType());
        return new PdtDto(deliveryDto.getWarehouseId(), deliveryDto.getDeliveryTime(), materialType);
    }
}
