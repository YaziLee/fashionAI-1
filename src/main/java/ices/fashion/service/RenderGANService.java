package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.dto.*;

import java.io.IOException;
import java.util.List;

public interface RenderGANService {

    public ApiResult<RenderGANDto> doRenderGenerate(RenderGANCriteria renderGANCriteria) throws IOException;
    public ApiResult<RenderInitDto> init();
}
