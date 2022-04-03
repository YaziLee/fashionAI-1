package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.VtoService;
import ices.fashion.service.dto.RenderInitDto;
import ices.fashion.service.dto.VtoDto;
import ices.fashion.service.dto.VtonInitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/vto")
public class VtoController {

    @Autowired
    private VtoService vtoService;

    @GetMapping("/doVto")
    public ApiResult<VtoDto> doVirtualTryOn(@RequestParam String clothFileName, @RequestParam String modelFileName) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()));
//        System.out.println(clothFileName);
//        System.out.println(modelFileName);
        ApiResult<VtoDto> res = vtoService.virtualTryOn(clothFileName, modelFileName);
        System.out.println(sdf.format(new Date()));
        return res;
    }

    @GetMapping("/init")
    public ApiResult<VtonInitDto> init() {
        return vtoService.init();
    }

}
