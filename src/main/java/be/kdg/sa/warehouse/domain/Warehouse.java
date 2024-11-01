package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Material material;

    private BigDecimal capacity;
    private BigDecimal occupancy;

    @ManyToOne
    private Seller seller;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Delivery> deliveries = new ArrayList<>();

    protected Warehouse() {}

    public Warehouse(Material material, BigDecimal capacity, BigDecimal occupancy, Seller seller) {
        this.material = material;
        this.capacity = capacity;
        this.occupancy = occupancy;
        this.seller = seller;
    }

    public UUID getId() {
        return id;
    }


    public Material getMaterial() {
        return material;
    }


    public BigDecimal getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(BigDecimal occupancy) {
        this.occupancy = occupancy;
    }

    public Seller getSellerName() {
        return seller;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }
}
