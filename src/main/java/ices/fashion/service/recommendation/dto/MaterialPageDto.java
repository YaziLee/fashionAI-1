package ices.fashion.service.recommendation.dto;

import ices.fashion.entity.recommendation.TBaseMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialPageDto extends TBaseMaterial {
    private String categoryName;
    private String brandName;
    private String recCategory;
}

