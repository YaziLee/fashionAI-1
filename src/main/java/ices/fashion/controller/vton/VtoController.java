package ices.fashion.controller.vton;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.DataType;
import ices.fashion.constant.ParamType;
import ices.fashion.service.vton.VtoService;
import ices.fashion.service.vton.dto.VtoDto;
import ices.fashion.service.vton.dto.VtonInitDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/vto")
public class VtoController {

    @Autowired
    private VtoService vtoService;

    @GetMapping("/doVto")
    @ApiOperation(value = "进行虚拟换装", notes = "两个参数分别是在七牛云的用户和游客url")
    @ApiImplicitParams({@ApiImplicitParam(name = "clothFileName", value = "衣服url", dataType = DataType.STRING,
            paramType = ParamType.QUERY), @ApiImplicitParam(name = "modelFileName", value = "模块url",
            dataType = DataType.STRING, paramType = ParamType.QUERY)})
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
    @ApiOperation(value = "初始化基本变量", notes = "")
    public ApiResult<VtonInitDto> init() {
        return vtoService.init();
    }

}
