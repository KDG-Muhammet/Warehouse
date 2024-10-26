package be.kdg.sa.warehouse.service.warehouse;

import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.repository.WarehouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final Logger logger = LoggerFactory.getLogger(WarehouseService.class);

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional(readOnly = true)
    public Collection<Warehouse> findAllWarehouses() {
        logger.info("   Finding all warehouses");
        return warehouseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Warehouse findWarehouseById(UUID id) {
        logger.info("   Finding warehouse with id {}", id);
        return warehouseRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Optional<Warehouse> findWarehouseBySellerUUIDAndMaterial_Id(UUID sellerUUID, UUID materialUUID) {
        logger.info("   Finding warehouse by seller {} and Material {}", sellerUUID, materialUUID);
        return warehouseRepository.findWarehouseBySellerUUIDAndMaterial_Id(sellerUUID, materialUUID);
    }

    @Transactional
    public void createWarehouse(Warehouse warehouse) {
        logger.info("   Creating warehouse: {}", warehouse.getMaterial());
        warehouseRepository.save(warehouse);
    }

}
