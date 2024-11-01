package be.kdg.sa.warehouse.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class DeliveryDto {
    private UUID uuid;
    private LocalDateTime deliveryTime;
    private String materialType;
    private UUID warehouseId;
    private BigDecimal amount;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }


    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
