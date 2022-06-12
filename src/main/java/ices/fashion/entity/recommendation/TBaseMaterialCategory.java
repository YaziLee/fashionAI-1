package ices.fashion.entity.recommendation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBaseMaterialCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String imgUrl;
    private String categoryName;
    private String description;
    private String recCategory;
    private Integer status;
}
