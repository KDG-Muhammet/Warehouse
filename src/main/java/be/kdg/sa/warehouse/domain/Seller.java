package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID UUID;

    @Column(unique = true)
    private String name;
    private String address;

    @OneToMany
    private List<Warehouse> warehouses = new ArrayList<>();

    @OneToOne
    private Invoice invoice;

    public Seller(String name, String address) {
        this.name = name;
        this.address = address;
    }

    protected Seller() {

    }

    public java.util.UUID getUUID() {
        return UUID;
    }

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<Warehouse> warehouses) {
        this.warehouses = warehouses;
    }

}
