package ices.fashion.service.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MMCGANModelDto {

    private Integer id;
    private String targetImage;
}
