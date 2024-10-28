package be.kdg.sa.warehouse.controller.dto;

import java.math.BigDecimal;

public class InvoiceDto {

    private SellerDto seller;
    private BigDecimal totalCommisionCost;
    private BigDecimal totalStorageCost;

    public InvoiceDto(SellerDto seller, BigDecimal totalCommisionCost, BigDecimal totalStorageCost) {
        this.seller = seller;
        this.totalCommisionCost = totalCommisionCost;
        this.totalStorageCost = totalStorageCost;
    }

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

    public void setTotalCommisionCost(BigDecimal totalCommisionCost) {
        this.totalCommisionCost = totalCommisionCost;
    }

    public BigDecimal getTotalStorageCost() {
        return totalStorageCost;
    }

    public void setTotalStorageCost(BigDecimal totalStorageCost) {
        this.totalStorageCost = totalStorageCost;
    }
}
