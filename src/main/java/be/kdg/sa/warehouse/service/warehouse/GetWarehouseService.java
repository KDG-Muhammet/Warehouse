package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.controller.dto.WarehouseDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class GetWarehouseService {
    private final WarehouseService warehouseService;
    private final ModelMapper modelMapper;

    public GetWarehouseService(WarehouseService warehouseService, ModelMapper modelMapper) {
        this.warehouseService = warehouseService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public List<WarehouseDto> findAllWarehouses() {
        return warehouseService.findAllWarehouses().stream().map(a -> modelMapper.map(a, WarehouseDto.class)).toList();
    }
}
