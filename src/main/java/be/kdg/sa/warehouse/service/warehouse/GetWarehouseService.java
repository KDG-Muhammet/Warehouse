package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.service.material.MaterialService;
import be.kdg.sa.warehouse.service.seller.SellerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetWarehouseService {
    private final WarehouseService warehouseService;
    private final MaterialService materialService;
    private final SellerService sellerService;
    private final ModelMapper modelMapper;

    public GetWarehouseService(WarehouseService warehouseService, MaterialService materialService, SellerService sellerService ,ModelMapper modelMapper) {
        this.warehouseService = warehouseService;
        this.materialService = materialService;
        this.sellerService = sellerService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<WarehouseDto> findAllWarehouses() {
        return warehouseService.findAllWarehouses().stream().map(a -> modelMapper.map(a, WarehouseDto.class)).toList();
    }

    @Transactional(readOnly = true)
    public UUID findWarehouseUUIDWithSellerAndMaterialType(String sellerName, String sellerAddress, String materialType) {
        Material material = materialService.findMaterialByType(materialType.toUpperCase());
        Optional<Seller> sellerOptional = sellerService.findSellerByName(sellerName);
        Seller seller = sellerOptional.orElseGet(() -> sellerService.createSeller(new Seller(sellerName, sellerAddress)));

        Optional<Warehouse> warehouseOptional = warehouseService.findWarehouseBySellerUUIDAndMaterial_Id(seller.getUUID(), material.getId());

        return warehouseOptional.map(Warehouse::getId).orElse(null);
    }
}
