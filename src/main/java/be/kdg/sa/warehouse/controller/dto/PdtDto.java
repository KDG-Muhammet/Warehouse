package be.kdg.sa.warehouse.controller.dto;

import be.kdg.sa.warehouse.domain.enums.MaterialType;

import java.time.LocalDateTime;
import java.util.UUID;

public class PdtDto {
    private UUID warehouseId;
    private MaterialType materialType;
    private LocalDateTime deliveryTime;

    public PdtDto(UUID warehouseId, LocalDateTime deliveryTime, MaterialType materialType) {
        this.warehouseId = warehouseId;
        this.deliveryTime = deliveryTime;
        this.materialType = materialType;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
