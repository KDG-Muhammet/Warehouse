package be.kdg.sa.warehouse.controller.dto.po;

import be.kdg.sa.warehouse.domain.po.OrderLine;

public class OrderLineDto {
    private double amount;
    private String materialName;

    public OrderLineDto(OrderLine orderLine) {
        this.amount = orderLine.getAmount();
        this.materialName = String.valueOf(orderLine.getMaterial().getType());
    }
    public OrderLineDto() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMaterialName() {
        return materialName;
    }


    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
