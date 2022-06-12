package ices.fashion.service.tools;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.tools.dto.MMCGANCriteria;
import ices.fashion.service.tools.dto.MMCGANDto;
import ices.fashion.service.tools.dto.MMCGANInitDto;

import java.io.IOException;

public interface MMCGANService {

    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException;

    public ApiResult<MMCGANInitDto> init() throws IOException;


}
