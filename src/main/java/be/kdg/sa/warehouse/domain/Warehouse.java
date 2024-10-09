package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    private Material material;

    private BigDecimal capacity;
    private BigDecimal occupancy;

    protected Warehouse() {}

    public Warehouse(Material material, BigDecimal capacity, BigDecimal occupancy) {
        this.material = material;
        this.capacity = capacity;
        this.occupancy = occupancy;
    }

    public UUID getId() {
        return id;
    }


    public Material getMaterial() {
        return material;
    }


    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(BigDecimal occupancy) {
        this.occupancy = occupancy;
    }
}
