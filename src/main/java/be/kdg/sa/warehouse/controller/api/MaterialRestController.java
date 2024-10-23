package be.kdg.sa.warehouse.controller.api;

import be.kdg.sa.warehouse.controller.dto.material.MaterialDto;
import be.kdg.sa.warehouse.controller.dto.material.UpdateMaterialRecord;
import be.kdg.sa.warehouse.domain.Material;
import be.kdg.sa.warehouse.service.material.FindMaterialService;
import be.kdg.sa.warehouse.service.material.UpdateMaterialService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/materials")
public class MaterialRestController {
    private final FindMaterialService findMaterialService;
    private final UpdateMaterialService updateMaterialService;

    public MaterialRestController(FindMaterialService findMaterialService, UpdateMaterialService updateMaterialService) {
        this.findMaterialService = findMaterialService;
        this.updateMaterialService = updateMaterialService;
    }


    @GetMapping("")
    public List<MaterialDto> getMaterials(){
        return findMaterialService.findAllMaterials();
    }


    @PutMapping("")
    public Material updateMaterialById(@Valid @RequestBody UpdateMaterialRecord updateMaterialRecord){
        return updateMaterialService.updateMaterialById(updateMaterialRecord.materialType(), new BigDecimal(updateMaterialRecord.sellingPrice()), new BigDecimal(updateMaterialRecord.storagePrice()));
    }
}
