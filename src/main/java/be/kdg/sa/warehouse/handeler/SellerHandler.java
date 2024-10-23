package be.kdg.sa.warehouse.handeler;

import be.kdg.sa.warehouse.config.RabbitTopology;
import be.kdg.sa.warehouse.controller.dto.SellerDto;
import be.kdg.sa.warehouse.service.seller.CreateSellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SellerHandler {
    private final CreateSellerService createSellerService;
    private final Logger logger = LoggerFactory.getLogger(SellerHandler.class);

    public SellerHandler(CreateSellerService createSellerService) {
        this.createSellerService = createSellerService;
    }

    @RabbitListener(queues = RabbitTopology.DIRECT_QUEUE_SELLER)
    public void receiveSeller(SellerDto sellerDto){
        logger.info("   Received Seller: {}", sellerDto.getSellerName());
        createSellerService.createSellerWithWarehouses(sellerDto);
    }

}
