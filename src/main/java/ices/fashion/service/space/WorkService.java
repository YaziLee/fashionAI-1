package ices.fashion.service.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TLike;
import ices.fashion.entity.space.TWork;
import ices.fashion.service.space.dto.ShareWorkCriteria;
import ices.fashion.service.space.dto.ShowDto;
import ices.fashion.service.space.dto.WorkDetailDto;

public interface WorkService {

    public ApiResult<ShowDto> getUserDesign(String id, Boolean isVisitor);

    public ApiResult<WorkDetailDto> getWorkDetail(Integer wid, String uid);

    public ApiResult saveOneWork(TWork work);

    public ApiResult shareWork(ShareWorkCriteria shareWorkCriteria);

    public ApiResult<ShowDto> getAllShareWork();

    public ApiResult cancelOneWorkShare(Integer wid);

    public ApiResult deleteOneWork(Integer wid);

    public ApiResult saveOneLike(TLike like);

    public ApiResult deleteOneLike(Integer id);

}
