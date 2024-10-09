package be.kdg.sa.warehouse.controller.dto;

import be.kdg.sa.warehouse.domain.enums.MaterialType;

import java.math.BigDecimal;

public class WarehouseDto {
    private MaterialType materialType;
    private BigDecimal capacity;
    private BigDecimal occupancy;

    public WarehouseDto() {}

    public WarehouseDto(MaterialType materialType, BigDecimal capacity, BigDecimal occupancy) {
        this.materialType = materialType;
        this.capacity = capacity;
        this.occupancy = occupancy;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(BigDecimal occupancy) {
        this.occupancy = occupancy;
    }
}
