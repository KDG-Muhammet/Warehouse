package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BuyerRepository extends JpaRepository<Buyer, UUID> {

    Buyer findBuyerByNameAndAddress(String name, String address);

}
