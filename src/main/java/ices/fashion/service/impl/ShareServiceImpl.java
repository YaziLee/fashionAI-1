package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.WorkConst;
import ices.fashion.entity.TShare;
import ices.fashion.entity.TWork;
import ices.fashion.mapper.ShareMapper;
import ices.fashion.mapper.WorkMapper;
import ices.fashion.service.ShareService;
import ices.fashion.service.dto.ShowDto;
import ices.fashion.util.WorkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private WorkUtil workUtil;

    @Override
    public ApiResult<ShowDto> getUserShare(String id) {
        QueryWrapper<TShare> shareQueryWrapper = new QueryWrapper<>();
        shareQueryWrapper.eq("phone", id).select("creator_cover_url", "creator_user_name", "work_category",
                "wid").eq("deleted", 0);
        List<TShare> shareList = shareMapper.selectList(shareQueryWrapper);

        List<TWork> workList = new ArrayList<>();
        for (TShare share : shareList) {
            workList.add(share.share2Work());
        }

        ShowDto showDto = workUtil.workList2ShowDto(workList);
        ApiResult<ShowDto> res = new ApiResult<>(200, "success");
        res.setData(showDto);
        return res;
    }


    /*

     */
    @Override
    public ApiResult<Integer> saveOneShare(TShare share) {
        if(shareMapper.insert(share) != 1) {
            return new ApiResult(800, "数据库更新失败");
        }
        ApiResult<Integer> res = new ApiResult(200, "success");
        QueryWrapper<TShare> shareQueryWrapper = new QueryWrapper<>();
        shareQueryWrapper.eq("deleted", 0)
                .eq("phone", share.getPhone())
                .eq("wid", share.getWid());
        TShare tmpShare = shareMapper.selectOne(shareQueryWrapper);
        res.setData(tmpShare.getId());
        return res;
    }

    @Override
    public ApiResult deleteOneShareById(Integer id) {
        TShare share = shareMapper.selectById(id);
        share.setDeleted(1);
        if(shareMapper.update(share, null) != 1) {
            return new ApiResult(800, "数据库更新失败");
        }
        return new ApiResult(200, "success");
    }
}
