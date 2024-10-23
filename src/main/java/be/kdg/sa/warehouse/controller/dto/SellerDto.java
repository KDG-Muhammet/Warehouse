package be.kdg.sa.warehouse.controller.dto;

public class SellerDto {
    private String sellerName;
    private String sellerAddress;

    public SellerDto(String sellerName, String sellerAddress) {
        this.sellerName = sellerName;
        this.sellerAddress = sellerAddress;
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
