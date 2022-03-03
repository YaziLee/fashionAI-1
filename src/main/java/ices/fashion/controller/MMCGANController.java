package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TMmc;
import ices.fashion.service.MMCGANService;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.MMCGANDto;
import ices.fashion.service.dto.MMCGANInitDto;
import ices.fashion.service.dto.VtoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/mmc")
public class MMCGANController {

    @Autowired
    private MMCGANService mmcganService;

    @PostMapping("/doMMCGAN")
    public ApiResult<MMCGANDto> doMMCGAN(@RequestBody MMCGANCriteria mmcganCriteria) throws IOException {

        ApiResult<MMCGANDto> res = mmcganService.doMMCGAN(mmcganCriteria);
        return res;
    }

    @GetMapping("/init")
    public ApiResult<MMCGANInitDto> init() throws IOException {
        return mmcganService.init();
    }

}
