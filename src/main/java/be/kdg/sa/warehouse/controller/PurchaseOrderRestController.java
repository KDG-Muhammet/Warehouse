package be.kdg.sa.warehouse.controller;


import be.kdg.sa.warehouse.controller.dto.po.PurchaseOrderDto;
import be.kdg.sa.warehouse.domain.po.PurchaseOrder;
import be.kdg.sa.warehouse.service.po.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrderRestController {
    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderRestController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping("/{poNumber}")
    public PurchaseOrderDto getPurchaseOrder(@PathVariable String poNumber) {
        return purchaseOrderService.findPurchaseOrderByPoNumber(poNumber);
    }


    @GetMapping("")
    public Collection<PurchaseOrderDto> getPurchaseOrders() {
        return purchaseOrderService.findAllOrders();
    }


    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseOrder addPurchaseOrder(@RequestBody @Valid PurchaseOrderDto purchaseOrderDto){
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDto);


    }


}
