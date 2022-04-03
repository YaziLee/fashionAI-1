package ices.fashion.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VtonInitDto {

    private List<String> clothMaleUpperLongList;
    private List<String> clothMaleUpperShortList;
    private List<String> imageMaleUpperLongList;
    private List<String> imageMaleUpperShortList;
    private List<String> clothFemaleUpperNoList;
    private List<String> clothFemaleUpperShortList;
    private List<String> imageFemaleUpperNoList;
    private List<String> imageFemaleUpperShortList;
}
