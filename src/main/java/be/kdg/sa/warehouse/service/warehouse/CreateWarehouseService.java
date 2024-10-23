package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.rmq.SenderPdtDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import be.kdg.sa.warehouse.service.seller.SellerService;
import be.kdg.sa.warehouse.service.material.MaterialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CreateWarehouseService {
    private final WarehouseService warehouseService;
    private final MaterialService materialService;
    private final SellerService sellerService;

    @Value("${spring.application.warehouse.capacity}")
    private BigDecimal capacity;

    @Value("${spring.application.warehouse.opacity}")
    private BigDecimal opacity;

    public CreateWarehouseService(WarehouseService warehouseService, MaterialService materialService, SellerService sellerService) {
        this.warehouseService = warehouseService;
        this.materialService = materialService;
        this.sellerService = sellerService;
    }

    @Transactional
    public Warehouse createOrUpdateWarehouse(SenderPdtDto senderPdtDto) {
        Material material = materialService.findMaterialByType(senderPdtDto.getMaterialType().toUpperCase());
        Optional<Seller> sellerOptional = sellerService.findSellerByName(senderPdtDto.getSellerName());
        Seller seller = sellerOptional.orElseGet(() -> sellerService.createSeller(new Seller(senderPdtDto.getSellerName(), senderPdtDto.getSellerAddress())));
        Optional<Warehouse> warehouseOptional = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(seller.getUUID(), material.getId());

        if (warehouseOptional.isPresent()) {
            return warehouseOptional.get();

        } else {
            Warehouse newWarehouse = new Warehouse(material, new BigDecimal(500000), new BigDecimal(0), seller);
            return warehouseService.createWarehouse(newWarehouse);
        }
    }

    @Transactional
    public void createWarehousesForNewSeller(Seller newSeller) {
        Arrays.stream(MaterialType.values()).forEach(type -> {
            Material material = materialService.findMaterialByType(type.toString().toUpperCase());
            warehouseService.createWarehouse(new Warehouse(material, capacity, opacity, newSeller));
        });
    }




}
