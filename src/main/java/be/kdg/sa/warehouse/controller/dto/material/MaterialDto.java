package be.kdg.sa.warehouse.controller.dto.material;

import be.kdg.sa.warehouse.domain.enums.MaterialType;

import java.math.BigDecimal;

public class MaterialDto {
    private MaterialType materialType;
    private BigDecimal storagePrice;
    private BigDecimal sellingPrice;


    public MaterialDto() {}

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }
}
