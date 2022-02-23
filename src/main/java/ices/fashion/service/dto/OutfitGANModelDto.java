package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutfitGANModelDto {

    private Integer id;
    private String imageBag;
    private String imageShoes;
    private String imageLower;
    private String imageUpper;

    private String bagFileName;
    private String shoesFileName;
    private String lowerFileName;
}
