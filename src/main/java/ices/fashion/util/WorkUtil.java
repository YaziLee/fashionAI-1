package ices.fashion.util;

import ices.fashion.constant.WorkConst;
import ices.fashion.entity.TWork;
import ices.fashion.service.dto.ShowDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkUtil {

    public ShowDto workList2ShowDto(List<TWork> workList) {
        ShowDto showDto = new ShowDto();

        showDto.setVtoList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.VTON))).collect(Collectors.toList()));
        showDto.setCollaborateList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.COLLABORATE))).collect(Collectors.toList()));
        showDto.setRecommendList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.RECOMMEND))).collect(Collectors.toList()));
        showDto.setMMCList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.MMC))).collect(Collectors.toList()));
        showDto.setOutfitList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.OUTFIT))).collect(Collectors.toList()));
        showDto.setRenderList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.RENDER))).collect(Collectors.toList()));
        return showDto;
    }
}
