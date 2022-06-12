package ices.fashion.service.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TShare;
import ices.fashion.service.space.dto.ShowDto;

public interface ShareService {

    public ApiResult<ShowDto> getUserShare(String id);

    public ApiResult<Integer> saveOneShare(TShare share);

    public ApiResult deleteOneShareById(Integer id);
}
