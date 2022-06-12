package ices.fashion.entity.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TOutfitBag {

    private Integer id;
    private String fileName;
    Integer deleted;
}
