package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialPageCriteria {
    private List<Integer> categoryIds;
    private List<Integer> brandIds;
    private Integer status; // 1: 有效，0：已删除
    private Integer currentPage;
    private Integer pageSize;
}
