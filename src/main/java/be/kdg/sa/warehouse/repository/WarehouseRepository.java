package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.domain.Warehouse;
import be.kdg.sa.warehouse.domain.enums.MaterialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    Warehouse findWarehouseByMaterial_Type(MaterialType materialType);
}
