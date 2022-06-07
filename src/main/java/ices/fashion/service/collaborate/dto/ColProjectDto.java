package ices.fashion.service.collaborate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColProjectDto {
    private Integer id;
    private String name;
    private String description;
    private Integer principle_id;
    private String principle_name;
    private Integer status;
}
