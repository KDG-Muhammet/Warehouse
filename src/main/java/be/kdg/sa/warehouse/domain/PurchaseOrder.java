package be.kdg.sa.warehouse.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID referenceUUID;

    private String poNumber;
    private Date purchaseDate;
    private String vesselNumber;

    @ManyToOne
    private Seller seller;
    @ManyToOne
    private Buyer buyer;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines  = new ArrayList<>();


    public PurchaseOrder(String poNumber, Date purchaseDate, String vesselNumber, Seller seller, Buyer buyer, List<OrderLine> orderLines) {
        this.poNumber = poNumber;
        this.purchaseDate = purchaseDate;
        this.vesselNumber = vesselNumber;
        this.seller = seller;
        this.buyer = buyer;
        this.orderLines = orderLines;
    }

    public PurchaseOrder() {

    }

    public UUID getReferenceUUID() {
        return referenceUUID;
    }

    public void setReferenceUUID(UUID referenceUUID) {
        this.referenceUUID = referenceUUID;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getVesselNumber() {
        return vesselNumber;
    }

    public void setVesselNumber(String vesselNumber) {
        this.vesselNumber = vesselNumber;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
}
