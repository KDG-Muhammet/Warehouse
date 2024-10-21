package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Buyer;
import be.kdg.sa.warehouse.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {

    Seller findSellerByNameAndAddress(String name, String address);

    Optional<Seller> findSellerByName(String name);
}
