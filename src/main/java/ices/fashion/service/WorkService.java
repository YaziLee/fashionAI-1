package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TWork;
import ices.fashion.service.dto.DesignDto;
import ices.fashion.service.dto.WorkDetailDto;

public interface WorkService {

    public ApiResult<DesignDto> getUserDesign(String id);

    public ApiResult<WorkDetailDto> getWorkDetail(Integer wid, String uid);

    public ApiResult saveOneWork(TWork work);
}
