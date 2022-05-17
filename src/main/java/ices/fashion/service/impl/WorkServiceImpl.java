package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.WorkConst;
import ices.fashion.entity.TWork;
import ices.fashion.mapper.WorkMapper;
import ices.fashion.service.WorkService;
import ices.fashion.service.dto.DesignDto;
import ices.fashion.service.dto.WorkDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkMapper workMapper;

    @Override
    //这个id目前的实现形式的phone
    public ApiResult<DesignDto> getUserDesign(String id) {
        QueryWrapper<TWork> workQueryWrapper = new QueryWrapper<>();
        workQueryWrapper.eq("phone", id).select("id", "cover_url", "user_name", "category");
        List<TWork> workList = workMapper.selectList(workQueryWrapper);

        DesignDto designDto = new DesignDto();
        designDto.setVtoList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.VTON))).collect(Collectors.toList()));
        designDto.setCollaborateList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.COLLABORATE))).collect(Collectors.toList()));
        designDto.setRecommendList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.RECOMMEND))).collect(Collectors.toList()));
        designDto.setMMCList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.MMC))).collect(Collectors.toList()));
        designDto.setOutfitList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.OUTFIT))).collect(Collectors.toList()));
        designDto.setRenderList(workList.stream().filter(e -> (e.getCategory() != null && e.getCategory()
                .equals(WorkConst.RENDER))).collect(Collectors.toList()));
        ApiResult<DesignDto> res = new ApiResult<>(200, "success");
        res.setData(designDto);
        return res;
    }

    @Override
    //这个uid目前的实现形式的phone
    public ApiResult<WorkDetailDto> getWorkDetail(Integer wid, String uid) {
        QueryWrapper<TWork> workQueryWrapper = new QueryWrapper<>();
        workQueryWrapper.eq("id", wid);
        TWork work = workMapper.selectOne(workQueryWrapper);
        WorkDetailDto data = new WorkDetailDto(work);
        ApiResult<WorkDetailDto> res = new ApiResult<>(200, "success");
        res.setData(data);
        return res;
    }

    @Override
    public ApiResult saveOneWork(TWork work) {
        if(workMapper.insert(work) != 1) {
            return new ApiResult(800, "数据库更新失败");
        }
        return new ApiResult(200, "success");
    }
}
