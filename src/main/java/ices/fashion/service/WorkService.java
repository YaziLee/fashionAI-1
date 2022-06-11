package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TWork;
import ices.fashion.service.dto.ShareWorkCriteria;
import ices.fashion.service.dto.ShowDto;
import ices.fashion.service.dto.WorkDetailDto;

public interface WorkService {

    public ApiResult<ShowDto> getUserDesign(String id, Boolean isVisitor);

    public ApiResult<WorkDetailDto> getWorkDetail(Integer wid, String uid);

    public ApiResult saveOneWork(TWork work);

    public ApiResult shareWork(ShareWorkCriteria shareWorkCriteria);

    public ApiResult<ShowDto> getAllShareWork();

    public ApiResult cancelOneWorkShare(Integer wid);
}
