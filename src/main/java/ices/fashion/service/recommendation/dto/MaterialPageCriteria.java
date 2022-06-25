package ices.fashion.service.recommendation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "MaterialPageCriteria", description = "素材分页查询条件")
public class MaterialPageCriteria {
    @ApiModelProperty("种类id列表")
    private List<Integer> categoryIds;
    @ApiModelProperty("品牌id列表")
    private List<Integer> brandIds;
    @ApiModelProperty("目标人群")
    private String targetPopulation;
    @ApiModelProperty("状态")
    private Integer status; // 1: 有效，0：已删除
    @ApiModelProperty("当前页数")
    private Integer currentPage;
    @ApiModelProperty("每页记录数")
    private Integer pageSize;
}
