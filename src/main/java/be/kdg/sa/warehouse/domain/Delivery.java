package be.kdg.sa.warehouse.domain;

import be.kdg.sa.warehouse.domain.enums.MaterialType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    private MaterialType materialType;

    private LocalDateTime deliveryDate;

    @ManyToOne
    private Warehouse warehouse;

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
}
