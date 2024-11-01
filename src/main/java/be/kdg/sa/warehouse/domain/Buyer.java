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

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
