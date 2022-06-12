package ices.fashion.service.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuitAuditCriteria {
    private Integer id;
    private Integer auditStatus;
    private String auditDescription;
}
