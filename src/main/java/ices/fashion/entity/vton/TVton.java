package ices.fashion.entity.vton;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TVton {

    private Integer id;
    private String fileName;
    private String category;
    private String description;
    private BigDecimal price;
}
