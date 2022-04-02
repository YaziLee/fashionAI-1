package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecCriteria {
    private Integer id;
    private String image1;
    private String image2;
    private String image3;
    private String matchType;
    public void init(String image1, String image2, String image3, String matchType) {
        this.id = 1;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.matchType = matchType;
    }
}
