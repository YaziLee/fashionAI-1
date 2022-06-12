package ices.fashion.service.dto;

import ices.fashion.entity.TBaseMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialPageDto extends TBaseMaterial {
    private String categoryName;
    private String brandName;
    private String recCategory;
}

