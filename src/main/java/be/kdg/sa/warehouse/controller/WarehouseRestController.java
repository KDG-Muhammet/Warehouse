package be.kdg.sa.warehouse.controller;

import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import be.kdg.sa.warehouse.service.warehouse.GetWarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
