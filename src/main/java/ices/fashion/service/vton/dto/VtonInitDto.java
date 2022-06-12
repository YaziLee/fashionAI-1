package ices.fashion.service.vton.dto;

import ices.fashion.entity.vton.TVton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VtonInitDto {

    private List<TVton> clothMaleUpperLongList;
    private List<TVton> clothMaleUpperShortList;
    private List<TVton> imageMaleUpperLongList;
    private List<TVton> imageMaleUpperShortList;
    private List<TVton> clothFemaleUpperNoList;
    private List<TVton> clothFemaleUpperShortList;
    private List<TVton> imageFemaleUpperNoList;
    private List<TVton> imageFemaleUpperShortList;
}
