package be.kdg.sa.warehouse.service.material;

import be.kdg.sa.warehouse.controller.dto.material.MaterialDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FindMaterialService {
    private final MaterialService materialService;
    private final ModelMapper modelMapper;

    public FindMaterialService(MaterialService materialService, ModelMapper modelMapper) {
        this.materialService = materialService;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<MaterialDto> findAllMaterials(){
        return materialService.findAllMaterials().stream().map(m -> modelMapper.map(m, MaterialDto.class)).toList();
    }
}
