package be.kdg.sa.warehouse.controller.api;


import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.service.po.CreatePurchaseOrderService;
import be.kdg.sa.warehouse.service.po.GetPurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrderRestController {
    private final GetPurchaseOrderService getPurchaseOrderService;
    private final CreatePurchaseOrderService createPurchaseOrderService;

    public PurchaseOrderRestController(GetPurchaseOrderService getPurchaseOrderService, CreatePurchaseOrderService createPurchaseOrderService) {
        this.getPurchaseOrderService = getPurchaseOrderService;
        this.createPurchaseOrderService = createPurchaseOrderService;
    }

    @GetMapping("/{poNumber}")
    public PurchaseOrderDto getPurchaseOrder(@PathVariable String poNumber) {
        return getPurchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
    }


    @GetMapping("")
    public Collection<PurchaseOrderDto> getPurchaseOrders() {
        return getPurchaseOrderService.findAllOrders();
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPurchaseOrder(@RequestBody @Valid PurchaseOrderDto purchaseOrderDto){
         createPurchaseOrderService.createPurchaseOrder(purchaseOrderDto);
    }


}
