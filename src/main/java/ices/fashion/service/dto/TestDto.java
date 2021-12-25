package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Integer id;
    private String imgUrl;
    private Integer categoryId;
    private Integer brandId;
    private String linkUrl;
    private BigDecimal price;
    private Integer status;
    private String description;
    private String targetPopulation;

    private boolean isTestParam;
}
