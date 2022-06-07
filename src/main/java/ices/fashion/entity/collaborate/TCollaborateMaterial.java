package ices.fashion.entity.collaborate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TCollaborateMaterial {

    private Integer id;
    private String fileName;
    private String category;
    private Integer deleted;
}
