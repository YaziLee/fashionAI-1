package ices.fashion.service.recommendation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "SuitPageCriteria", description = "套装分页查询条件")
public class SuitPageCriteria {
    @ApiModelProperty("创作者手机号")
    private String customerId;
    @ApiModelProperty("状态")
    private List<Integer> status; // 0: 已删除，1：草稿，2：发布到个人空间，3：发布到广场
    @ApiModelProperty("审核状态")
    private List<Integer> auditStatus; // 0: 未审核，1：通过，2：未通过
    @ApiModelProperty("当前页数")
    private Integer currentPage;
    @ApiModelProperty("每页记录数")
    private Integer pageSize;
}
