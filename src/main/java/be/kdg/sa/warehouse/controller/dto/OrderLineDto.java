package be.kdg.sa.warehouse.controller.dto;

import be.kdg.sa.warehouse.domain.OrderLine;

public class OrderLineDto {
    private int amount;
    private String materialName;

    public OrderLineDto(OrderLine orderLine) {
        this.amount = orderLine.getAmount();
        this.materialName = String.valueOf(orderLine.getMaterial().getType());
    }
    public OrderLineDto() {
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }
}
