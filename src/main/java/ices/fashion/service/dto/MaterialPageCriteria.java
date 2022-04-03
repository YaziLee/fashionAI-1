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
    private Integer categoryId;
    private List<Integer> brandIds;
    private Integer currentPage;
    private Integer pageSize;
}
