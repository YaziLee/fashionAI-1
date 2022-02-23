package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MMCGANCriteria {

    private String fileName;
    private Integer id;
    private String originalText;
    private String targetCategory;
    private String targetText;
    private String originalImage;

    public void init() {
        this.id = 1;
        this.fileName = null;
    }
}
