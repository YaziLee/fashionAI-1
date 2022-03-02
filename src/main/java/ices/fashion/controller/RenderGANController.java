package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.RenderGANService;
import ices.fashion.service.dto.RenderGANCriteria;
import ices.fashion.service.dto.RenderGANDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

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

    @GetMapping("/init")
    public ApiResult<List<String>> init() {
        return renderGANService.init();
    }
}
