package be.kdg.sa.warehouse.service.seller;

import be.kdg.sa.warehouse.controller.dto.SellerDto;
import be.kdg.sa.warehouse.domain.Seller;
import be.kdg.sa.warehouse.service.invoice.InvoiceService;
import be.kdg.sa.warehouse.service.warehouse.CreateWarehouseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CreateSellerService {
    private final SellerService sellerService;
    private final CreateWarehouseService createWarehouseService;
    private final InvoiceService invoiceService;

    public CreateSellerService(SellerService sellerService, CreateWarehouseService createWarehouseService, InvoiceService invoiceService) {
        this.sellerService = sellerService;
        this.createWarehouseService = createWarehouseService;
        this.invoiceService = invoiceService;
    }

    @Transactional
    public Seller createSellerWithWarehouses(SellerDto sellerDto) {
        Optional<Seller> optionalSeller = sellerService.findSellerByName(sellerDto.getSellerName());

        if (optionalSeller.isEmpty()) {
            Seller seller = sellerService.createSeller(new Seller(sellerDto.getSellerName(), sellerDto.getSellerAddress()));
            createWarehouseService.createWarehousesForNewSeller(seller);
            invoiceService.createInvoiceForNewSeller(seller);
            return seller;
        }
        return optionalSeller.get();
    }
}

