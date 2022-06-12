package ices.fashion.controller.tools;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.tools.RenderGANService;
import ices.fashion.service.tools.dto.RenderGANCriteria;
import ices.fashion.service.tools.dto.RenderGANDto;
import ices.fashion.service.tools.dto.RenderInitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/render")
public class RenderGANController {

    @Autowired
    private RenderGANService renderGANService;

    @PostMapping("/doRender")
    public ApiResult<RenderGANDto> doRender(@RequestBody RenderGANCriteria renderGANCriteria) throws IOException {
        try {
            ApiResult<RenderGANDto> res = renderGANService.doRenderGenerate(renderGANCriteria);
            return res;
        }catch (Exception e){
            ApiResult<RenderGANDto> res = new ApiResult<>(500,e.getMessage());
            return res;
        }
    }

    @GetMapping("/init")
    public ApiResult<RenderInitDto> init() {
        return renderGANService.init();
    }
}
