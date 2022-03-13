package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecCriteria {
    private Integer upperId;
    private Integer bottomId;
    private Integer shoeId;
    private Integer bagId;
    private String matchType;
    public void init() {
        this.upperId = null;
        this.bottomId = null;
        this.shoeId = null;
        this.bagId = null;
        this.matchType = null;
    }
}
