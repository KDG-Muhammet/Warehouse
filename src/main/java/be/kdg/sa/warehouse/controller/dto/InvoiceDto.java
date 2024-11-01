package be.kdg.sa.warehouse.controller.dto;

import java.math.BigDecimal;

public class InvoiceDto {

    private SellerDto seller;
    private BigDecimal totalCommisionCost;
    private BigDecimal totalStorageCost;

    public InvoiceDto() {

    }

    public SellerDto getSeller() {
        return seller;
    }

    public void setSeller(SellerDto seller) {
        this.seller = seller;
    }

    public BigDecimal getTotalCommisionCost() {
        return totalCommisionCost;
    }

    public BigDecimal getTotalStorageCost() {
        return totalStorageCost;
    }

}
