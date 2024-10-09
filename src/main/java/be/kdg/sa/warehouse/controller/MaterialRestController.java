package be.kdg.sa.warehouse.controller;

import be.kdg.sa.warehouse.controller.dto.MaterialDto;
import be.kdg.sa.warehouse.service.MaterialService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialRestController {
    private final MaterialService materialService;

    public MaterialRestController(MaterialService materialService) {
        this.materialService = materialService;
    }


    @GetMapping("")
    public List<MaterialDto> getMaterials(){
        return materialService.findAllMaterials();
    }


}
