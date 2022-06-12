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
public class TBaseMaterialBrand {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String imgUrl;
    private String brandName;
    private String description;
    private Integer status;
}
