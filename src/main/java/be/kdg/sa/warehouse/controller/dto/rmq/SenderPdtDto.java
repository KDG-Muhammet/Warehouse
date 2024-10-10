package be.kdg.sa.warehouse.controller.dto.rmq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SenderPdtDto implements Serializable {
    private LocalDateTime deliveryTime;
    private String warehouse;
    private BigDecimal materialWeight;

    public SenderPdtDto() {}

    public SenderPdtDto(LocalDateTime deliveryTime, String warehouse, BigDecimal materialWeight) {
        this.deliveryTime = deliveryTime;
        this.warehouse = warehouse;
        this.materialWeight = materialWeight;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(BigDecimal materialWeight) {
        this.materialWeight = materialWeight;
    }
}
