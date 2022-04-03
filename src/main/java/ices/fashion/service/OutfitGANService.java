package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.dto.*;

import java.io.IOException;

public interface OutfitGANService {
    public ApiResult<OutfitGANDto> doOutfitGAN(OutfitGANCriteria outfitGANCriteria) throws IOException;
    public ApiResult<OutfitGANInitDto> init();
}
