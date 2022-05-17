package ices.fashion.service.dto;

import ices.fashion.entity.TWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignDto {

    //每个list只有id,userName,coverUrl, category有值
    List<TWork> vtoList;
    List<TWork> recommendList;
    List<TWork> collaborateList;
    List<TWork> MMCList;
    List<TWork> RenderList;
    List<TWork> outfitList;

}
