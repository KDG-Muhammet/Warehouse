package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID UUID;
    private String name;
    private String address;

    public Buyer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    protected Buyer() {

    }

    public UUID getId() {
        return UUID;
    }

    public void setId(UUID UUID) {
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
