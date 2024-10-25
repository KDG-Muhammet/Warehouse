package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @OneToOne
    private Seller seller;

    private BigDecimal totalCommisionCost;

    public Invoice(Seller seller) {
        this.seller = seller;
    }

    protected Invoice() {

    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public BigDecimal getTotalCommisionCost() {
        return totalCommisionCost;
    }

    public void setTotalCommisionCost(BigDecimal totalCommisionCost) {
        this.totalCommisionCost = totalCommisionCost;
    }
}
