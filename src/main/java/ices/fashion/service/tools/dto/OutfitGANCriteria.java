package ices.fashion.service.tools.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitGANCriteria {

    private String upperFileName;
    private String bagFileName;
    private String shoesFileName;
    private String lowerFileName;

    private Integer id;
    private String maskUpper;
    private String imageCategory;
    private String image;
    private String maskLower;
    private String maskBag;
    private String maskShoes;

    public void init() {
        this.id = 1;
        this.maskUpper = null;
        this.imageCategory = "upper";
        this.upperFileName = null;
        this.bagFileName = null;
        this.shoesFileName = null;
        this.lowerFileName = null;
        this.maskShoes = null;
        this.maskBag = null;
        this.maskLower = null;
    }
}
