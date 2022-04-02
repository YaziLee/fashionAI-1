package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.RenderGANService;
import ices.fashion.service.dto.RenderGANCriteria;
import ices.fashion.service.dto.RenderGANDto;
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

        ApiResult<RenderGANDto> res = renderGANService.doRenderGenerate(renderGANCriteria);
        return res;
    }
}
