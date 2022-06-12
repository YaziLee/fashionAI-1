package ices.fashion.service.recommendation.dto;

import ices.fashion.entity.recommendation.TBaseSuit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuitAdminPageDto extends TBaseSuit {
    private String username;
    private String phone;
}
