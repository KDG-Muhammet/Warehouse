package be.kdg.sa.warehouse.controller.dto.material;

import jakarta.validation.constraints.NotBlank;

public record UpdateMaterialRecord(
        @NotBlank String materialType,
        @NotBlank String sellingPrice,
        @NotBlank String storagePrice
) {
}
