package ices.fashion.controller.tools;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.tools.MMCGANService;
import ices.fashion.service.tools.dto.MMCGANCriteria;
import ices.fashion.service.tools.dto.MMCGANDto;
import ices.fashion.service.tools.dto.MMCGANInitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/mmc")
public class MMCGANController {

    @Autowired
    private MMCGANService mmcganService;

    @PostMapping("/doMMCGAN")
    public ApiResult<MMCGANDto> doMMCGAN(@RequestBody MMCGANCriteria mmcganCriteria) throws IOException {
        try{
            ApiResult<MMCGANDto> res = mmcganService.doMMCGAN(mmcganCriteria);
            return res;
        }catch (Exception e){
            ApiResult<MMCGANDto> error = new ApiResult<>(500,e.getMessage());
            return error;
        }

    }

    @GetMapping("/init")
    public ApiResult<MMCGANInitDto> init() throws IOException {
        return mmcganService.init();
    }

}
