package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {

    Delivery findDeliveryByDeliveryDate(LocalDateTime deliveryDate);
}
