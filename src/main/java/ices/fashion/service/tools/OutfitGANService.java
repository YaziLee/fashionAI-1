package ices.fashion.service.tools;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.tools.dto.OutfitGANCriteria;
import ices.fashion.service.tools.dto.OutfitGANDto;
import ices.fashion.service.tools.dto.OutfitGANInitDto;

import java.io.IOException;

public interface OutfitGANService {
    public ApiResult<OutfitGANDto> doOutfitGAN(OutfitGANCriteria outfitGANCriteria) throws IOException;
    public ApiResult<OutfitGANInitDto> init();
}
