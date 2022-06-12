package ices.fashion.service.tools;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.tools.dto.RenderGANCriteria;
import ices.fashion.service.tools.dto.RenderGANDto;
import ices.fashion.service.tools.dto.RenderInitDto;

import java.io.IOException;

public interface RenderGANService {

    public ApiResult<RenderGANDto> doRenderGenerate(RenderGANCriteria renderGANCriteria) throws IOException;
    public ApiResult<RenderInitDto> init();
}
