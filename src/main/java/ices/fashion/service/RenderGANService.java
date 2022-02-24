package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.dto.OutfitGANCriteria;
import ices.fashion.service.dto.OutfitGANDto;
import ices.fashion.service.dto.RenderGANCriteria;
import ices.fashion.service.dto.RenderGANDto;

import java.io.IOException;

public interface RenderGANService {

    public ApiResult<RenderGANDto> doRenderGenerate(RenderGANCriteria renderGANCriteria) throws IOException;
}
