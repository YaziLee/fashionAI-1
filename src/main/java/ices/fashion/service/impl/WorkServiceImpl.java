package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.WorkConst;
import ices.fashion.entity.TWork;
import ices.fashion.mapper.WorkMapper;
import ices.fashion.service.ColVersionService;
import ices.fashion.service.WorkService;
import ices.fashion.service.dto.ShareWorkCriteria;
import ices.fashion.service.dto.ShowDto;
import ices.fashion.service.dto.WorkDetailDto;
import ices.fashion.util.WorkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkUtil workUtil;

    @Autowired
    private ColVersionService colVersionService;

    @Override
    //这个id目前的实现形式的phone
    public ApiResult<ShowDto> getUserDesign(String id) {
        QueryWrapper<TWork> workQueryWrapper = new QueryWrapper<>();
        workQueryWrapper.eq("phone", id).select("id", "cover_url", "user_name", "category");
        List<TWork> workList = workMapper.selectList(workQueryWrapper);

        ShowDto showDto = workUtil.workList2ShowDto(workList);
        ApiResult<ShowDto> res = new ApiResult<>(200, "success");
        res.setData(showDto);
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
        System.out.println(work.getCategory());
        if(work.getCategory().equals("collaborate") ){
            String workStr = work.getWorkDescription();
            colVersionService.updateSaved(workStr,1);
        }
        return new ApiResult(200, "success");
    }

    @Override
    /*
    将当前work id
    的作品分享
     */
    public ApiResult shareWork(ShareWorkCriteria shareWorkCriteria) {
        int id = shareWorkCriteria.getId();
        TWork work = workMapper.selectById(id);
        work.setWorkShared(1);
        work.setTitle(shareWorkCriteria.getTitle());
        work.setWordDescription(shareWorkCriteria.getWordDescription());
        if(workMapper.updateById(work) != 1) {
            return new ApiResult(800, "数据库更新失败");
        }
        return new ApiResult(200, "success");
    }

    @Override
    /*
    分享区得到所有分享的作品
     */
    public ApiResult<ShowDto> getAllShareWork() {
        QueryWrapper<TWork> workQueryWrapper = new QueryWrapper<>();
        workQueryWrapper.eq("work_shared", 1).select("id", "cover_url", "user_name", "category");
        List<TWork> workList = workMapper.selectList(workQueryWrapper);

        ShowDto showDto = workUtil.workList2ShowDto(workList);
        ApiResult<ShowDto> res = new ApiResult<>(200, "success");
        res.setData(showDto);
        return res;
    }



}
