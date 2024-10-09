package be.kdg.sa.warehouse.domain;

import be.kdg.sa.warehouse.domain.enums.MaterialType;
import jakarta.persistence.*;


import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialType type;

    private String description;
    private BigDecimal storagePrice;
    private BigDecimal sellingPrice;

    protected Material() {}

    public Material(MaterialType type, String description, BigDecimal storagePrice, BigDecimal sellingPrice) {
        this.type = type;
        this.description = description;
        this.storagePrice = storagePrice;
        this.sellingPrice = sellingPrice;
    }

    public UUID getId() {
        return id;
    }

    public MaterialType getType() {
        return type;
    }

    public void setType(MaterialType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

