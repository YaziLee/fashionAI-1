package ices.fashion.entity.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TRender {

    private Integer id;
    private String fileName;
    private String category;
    private String type;
    private Integer deleted;
}
