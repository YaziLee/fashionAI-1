package ices.fashion.service.dto;

import ices.fashion.entity.TWork;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
主要用于个人空间展示和广场
 */
public class ShowDto {

    //每个list只有id, userName, coverUrl, category title有值
    List<TWork> vtoList;
    List<TWork> recommendList;
    List<TWork> collaborateList;
    List<TWork> MMCList;
    List<TWork> RenderList;
    List<TWork> outfitList;

}
