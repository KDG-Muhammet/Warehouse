package be.kdg.sa.warehouse.controller.dto;

import be.kdg.sa.warehouse.domain.enums.MaterialType;

import java.math.BigDecimal;

public class WarehouseDto {
    private MaterialType materialType;
    private BigDecimal capacity;
    private BigDecimal occupancy;

    public WarehouseDto() {}


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
