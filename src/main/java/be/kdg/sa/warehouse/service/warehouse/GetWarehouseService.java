package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.SellerDto;
import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.service.material.MaterialService;
import be.kdg.sa.warehouse.service.seller.CreateSellerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetWarehouseService {
    private final WarehouseService warehouseService;
    private final MaterialService materialService;
    private final CreateSellerService createSellerService;
    private final ModelMapper modelMapper;
    @Value("${warehouseCapacity}")
    private BigDecimal warehouseCapacity;
    @Value("${percentage}")
    private BigDecimal percentageValue;

    public GetWarehouseService(WarehouseService warehouseService,
                               MaterialService materialService,
                               CreateSellerService createSellerService,
                               ModelMapper modelMapper) {
        this.warehouseService = warehouseService;
        this.materialService = materialService;
        this.createSellerService = createSellerService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<WarehouseDto> findAllWarehouses() {
        return warehouseService.findAllWarehouses().stream().map(a -> modelMapper.map(a, WarehouseDto.class)).toList();
    }

    @Transactional(readOnly = true)
    public UUID findWarehouseUUIDWithSellerAndMaterialType(String sellerName, String sellerAddress, String materialType) {
        Material material = materialService.findMaterialByType(materialType.toUpperCase());
        Seller seller = createSellerService.createSellerWithWarehouses(new SellerDto(sellerName, sellerAddress));

        Optional<Warehouse> warehouseOptional = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(seller.getUUID(), material.getId());

        return warehouseOptional.map(Warehouse::getId).orElse(null);
    }

    @Transactional(readOnly = true)
    public BigDecimal findWarehouseOccupancyPercentage(UUID warehouseId) {
        BigDecimal occupancy = warehouseService.findWarehouseById(warehouseId).getOccupancy();
        return (occupancy.divide(warehouseCapacity, 7, RoundingMode.HALF_UP)).multiply(percentageValue);
    }
}
