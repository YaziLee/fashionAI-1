package ices.fashion.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TBaseSuit {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private Integer customerId;
    private String materialIds;
    private String imgUrl;
    private Integer status;
    private Integer auditStatus;
    private String auditDescription;
    private BigDecimal price;
    private Integer scanCount;
    private Integer collectCount;
    private String description;
}
