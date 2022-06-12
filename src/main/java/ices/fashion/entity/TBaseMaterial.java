package ices.fashion.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBaseMaterial {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String imgUrl;
    private Integer categoryId;
    private Integer brandId;
    private String linkUrl;
    private BigDecimal price;
    private Integer status;
    private String description;
    private String targetPopulation;
}
