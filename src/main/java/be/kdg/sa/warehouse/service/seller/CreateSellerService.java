package be.kdg.sa.warehouse.service.seller;

import be.kdg.sa.warehouse.controller.dto.SellerDto;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.service.warehouse.CreateWarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateSellerService {
    private final SellerService sellerService;
    private final CreateWarehouseService createWarehouseService;

    public CreateSellerService(SellerService sellerService, CreateWarehouseService createWarehouseService) {
        this.sellerService = sellerService;
        this.createWarehouseService = createWarehouseService;
    }

    @Transactional
    public void createSellerWithWarehouses(SellerDto sellerDto) {
        Seller seller = sellerService.createSeller(new Seller(sellerDto.getSellerName(), sellerDto.getSellerAddress()));
        createWarehouseService.createWarehousesForNewSeller(seller);
    }
}

