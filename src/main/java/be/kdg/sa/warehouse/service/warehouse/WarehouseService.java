package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.repository.WarehouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Warehouse> findAllWarehouses() {
        logger.info("Finding all warehouses");
        return warehouseRepository.findAll();
    }


}
