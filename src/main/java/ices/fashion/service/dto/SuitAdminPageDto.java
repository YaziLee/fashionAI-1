package ices.fashion.service.dto;

import ices.fashion.entity.TBaseSuit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuitAdminPageDto extends TBaseSuit {
    private String username;
    private String phone;
}
