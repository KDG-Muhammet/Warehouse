package be.kdg.sa.warehouse.controller.dto;

public class SellerDto {
    private String sellerName;
    private String sellerAddress;

    public SellerDto(String sellerName, String sellerAddress) {
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
    }

    public SellerDto() {
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }
}
