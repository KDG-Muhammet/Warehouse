package be.kdg.sa.warehouse.controller.dto.po;

import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.domain.enums.Status;

import java.util.Date;
import java.util.List;


public class PurchaseOrderDto {
    private String poNumber;
    private String buyerName;
    private String buyerAddress;
    private String sellerName;
    private String sellerAddress;
    private Date date;
    private List<OrderLineDto> orderLines;
    private Status status;

    public PurchaseOrderDto(PurchaseOrder purchaseOrder) {
        this.poNumber = purchaseOrder.getPoNumber();
        this.buyerName = purchaseOrder.getBuyer().getName();
        this.buyerAddress = purchaseOrder.getBuyer().getAddress();
        this.sellerName = purchaseOrder.getSeller().getName();
        this.sellerAddress = purchaseOrder.getSeller().getAddress();
        this.date = purchaseOrder.getPurchaseDate();
        this.status = purchaseOrder.getStatus();
    }


    public PurchaseOrderDto() {}

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }


    public String getSellerAddress() {
        return sellerAddress;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

