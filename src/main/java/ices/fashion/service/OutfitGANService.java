package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.OutfitGANCriteria;
import ices.fashion.service.dto.OutfitGANDto;

import java.io.IOException;

public interface OutfitGANService {
    public ApiResult<OutfitGANDto> doOutfitGAN(OutfitGANCriteria outfitGANCriteria) throws IOException;
}
