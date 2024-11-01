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

    public BigDecimal getStoragePrice() {
        return storagePrice;
    }

    public void setStoragePrice(BigDecimal storagePrice) {
        this.storagePrice = storagePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
