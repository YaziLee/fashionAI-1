package ices.fashion.entity.recommendation;

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
    private String customerId;
    private String materialIds;
    private String imgUrl;
    private String canvas; // 画布信息
    private Integer status;// 0: 已删除，1：草稿，2：发布到个人空间，3：发布到广场
    private Integer auditStatus;
    private String auditDescription;
    private BigDecimal price;
    private Integer collectCount;
    private Integer scanCount;
    private Integer likeCount;
    private String description;

}
