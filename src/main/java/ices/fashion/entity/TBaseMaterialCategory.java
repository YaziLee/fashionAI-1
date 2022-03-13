package ices.fashion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBaseMaterialCategory {
    private Integer id;
    private String imgUrl;
    private String categoryName;
    private String description;
    private Integer status;
}
