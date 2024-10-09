package be.kdg.sa.warehouse.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID UUID;
    private String name;
    private String address;

    public Seller(UUID UUID, String name, String address) {
        this.UUID = UUID;
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
}
