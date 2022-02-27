package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.VtoDto;

import java.io.IOException;

public interface MMCGANService {

    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException;
}
