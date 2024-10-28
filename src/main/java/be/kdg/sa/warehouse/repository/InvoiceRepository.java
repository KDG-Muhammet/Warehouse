package be.kdg.sa.warehouse.repository;

import be.kdg.sa.warehouse.domain.Invoice;
import be.kdg.sa.warehouse.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    Invoice findInvoiceBySeller(Seller seller);
    Invoice findInvoiceBySellerName(String sellerName);
}
