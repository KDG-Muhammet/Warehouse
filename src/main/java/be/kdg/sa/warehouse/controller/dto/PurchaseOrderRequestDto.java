package be.kdg.sa.warehouse.controller.dto;

public class PurchaseOrderRequestDto {
    private String poNumber;
    private String vesselNumber;

    public PurchaseOrderRequestDto(String poNumber, String vesselNumber) {
        this.poNumber = poNumber;
        this.vesselNumber = vesselNumber;
    }

    public String getPurchaseOrderNumber() {
        return poNumber;
    }

    public void setPurchaseOrderNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }
}
