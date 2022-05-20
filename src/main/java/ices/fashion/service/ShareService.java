package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TShare;
import ices.fashion.service.dto.ShowDto;

public interface ShareService {

    public ApiResult<ShowDto> getUserShare(String id);

    public ApiResult saveOneShare(TShare share);
}
