package be.kdg.sa.warehouse.controller.dto.rmq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SenderPdtDto implements Serializable {
    private LocalDateTime deliveryTime;
    private String materialType;
    private BigDecimal materialWeight;
    private String sellerName;
    private String sellerAddress;

    public SenderPdtDto() {}

    public SenderPdtDto(LocalDateTime deliveryTime, String materialType, BigDecimal materialWeight, String sellerName, String sellerAddress) {
        this.deliveryTime = deliveryTime;
        this.materialType = materialType;
        this.materialWeight = materialWeight;
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public BigDecimal getMaterialWeight() {
        return materialWeight;
    }

    public void setMaterialWeight(BigDecimal materialWeight) {
        this.materialWeight = materialWeight;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }
}
