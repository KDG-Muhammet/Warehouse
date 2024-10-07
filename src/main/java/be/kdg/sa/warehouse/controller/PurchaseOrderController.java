package be.kdg.sa.warehouse.controller;


import be.kdg.sa.warehouse.controller.dto.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.PurchaseOrder;
import be.kdg.sa.warehouse.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrderController {
    private PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/{poNumber}")
    public PurchaseOrderDto getPurchaseOrder(@PathVariable String poNumber) {
        return purchaseOrderService.getPurchaseOrderByPoNumber(poNumber);
    }


    @GetMapping("")
    public Collection<PurchaseOrderDto> getPurchaseOrders() {
        return purchaseOrderService.getAllOrders();
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrder addPurchaseOrder(@RequestBody @Valid PurchaseOrderDto purchaseOrderDto){
        return purchaseOrderService.makeOrder(purchaseOrderDto);


    }


}
