package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuitPageCriteria {
    private String customerId;
    private List<Integer> status; // 0: 已删除，1：草稿，2：发布到个人空间，3：发布到广场
    private List<Integer> auditStatus; // 0: 未审核，1：通过，2：未通过
    private Integer currentPage;
    private Integer pageSize;
}
