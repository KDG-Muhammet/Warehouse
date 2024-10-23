package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.service.warehouse.GetWarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseRestController {
    private final GetWarehouseService getWarehouseService;

    public WarehouseRestController(GetWarehouseService getWarehouseService) {
        this.getWarehouseService = getWarehouseService;
    }

    @GetMapping("")
    public List<WarehouseDto> findAllWarehouses() {
        return getWarehouseService.findAllWarehouses();
    }

    @GetMapping("/{type}/{sellerName}/{sellerAddress}")
    public UUID findWarehouseUUIDWithSellerNameAndMaterialType(@PathVariable String type, @PathVariable String sellerName, @PathVariable String sellerAddress) {
        return getWarehouseService.findWarehouseUUIDWithSellerAndMaterialType(sellerName, sellerAddress,type);
    }
}
