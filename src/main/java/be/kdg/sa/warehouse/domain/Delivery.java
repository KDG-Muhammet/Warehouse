package be.kdg.sa.warehouse.domain;

import be.kdg.sa.warehouse.domain.enums.MaterialType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private MaterialType materialType;

    private LocalDateTime deliveryDate;

    @ManyToOne
    private Warehouse warehouse;

    private BigDecimal amount;

    private BigDecimal costPrice;

    private int days;

    private BigDecimal storagePrice;

    public Delivery(MaterialType materialType, LocalDateTime deliveryDate, Warehouse warehouse) {
        this.materialType = materialType;
        this.deliveryDate = deliveryDate;
        this.warehouse = warehouse;
    }

    protected Delivery() {

    }


    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public BigDecimal getStoragePrice() {
        return storagePrice;
    }

    public void setStoragePrice(BigDecimal storagePrice) {
        this.storagePrice = storagePrice;
    }
}
