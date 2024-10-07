package be.kdg.sa.warehouse.controller;

import be.kdg.sa.warehouse.service.MaterialService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialController {

    private final MaterialService materialService;


    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }
}
