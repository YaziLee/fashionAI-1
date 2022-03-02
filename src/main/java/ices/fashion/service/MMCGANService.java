package ices.fashion.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TMmc;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.VtoDto;

import java.io.IOException;
import java.util.List;

public interface MMCGANService {

    public ApiResult<MMCGANDto> doMMCGAN(MMCGANCriteria mmcganCriteria) throws IOException;

    public ApiResult<List<TMmc>> init() throws IOException;


}
