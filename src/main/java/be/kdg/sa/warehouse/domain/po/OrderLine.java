package be.kdg.sa.warehouse.domain.po;

import be.kdg.sa.warehouse.domain.Material;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderLineId;
    private double amount;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Material material;
    @ManyToOne
    private PurchaseOrder purchaseOrder;

    public OrderLine(double amount, Material material) {
        this.amount = amount;
        this.material = material;

    }

    protected OrderLine() {
    }

    public double getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }


    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
