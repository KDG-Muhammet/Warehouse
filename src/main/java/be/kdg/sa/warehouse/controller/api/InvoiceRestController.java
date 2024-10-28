package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.InvoiceDto;
import be.kdg.sa.warehouse.service.invoice.InvoiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceRestController {

    private final InvoiceService invoiceService;

    public InvoiceRestController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("")
    public List<InvoiceDto> getInvoices(){
       return invoiceService.findAllInvoices();
    }
    @GetMapping("{seller}")
    public InvoiceDto getInvoice(@PathVariable String seller){
        return invoiceService.findInvoiceBySellerName(seller);
    }

    @PutMapping("")
    public void updateInvoices(){
        invoiceService.calculateStorageCostsAndGenerateInvoices();
    }
}
