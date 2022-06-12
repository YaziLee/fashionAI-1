package ices.fashion.service.space.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TShare;
import ices.fashion.entity.space.TWork;
import ices.fashion.mapper.space.ShareMapper;
import ices.fashion.mapper.space.WorkMapper;
import ices.fashion.service.space.ShareService;
import ices.fashion.service.space.dto.ShowDto;
import ices.fashion.util.WorkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        shareMapper.update(share, null);
        return new ApiResult(200, "success");
    }
}
