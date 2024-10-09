package be.kdg.sa.warehouse.controller.dto.po;

public class PurchaseOrderRequestDto {
    private String poNumber;
    private String vesselNumber;

    public PurchaseOrderRequestDto(String poNumber, String vesselNumber) {
        this.poNumber = poNumber;
        this.vesselNumber = vesselNumber;
    }

    public PurchaseOrderRequestDto() {
    }


    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }
}
