package ices.fashion.controller.collaborate;

import ices.fashion.constant.DataType;
import ices.fashion.service.collaborate.ColVersionService;
import ices.fashion.service.collaborate.dto.ColVersionDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class VersionController {
    @Autowired
    private ColVersionService colVersionService;

    @GetMapping("/getVersion")
    @ApiOperation(value = "获取该项目的所有version信息")
    @ApiImplicitParam(name = "pid",value = "项目id",required = true,dataType = DataType.INT)
    public List<ColVersionDto> getVersion(@RequestParam(value="pid") int pid){
        return colVersionService.getVersion(pid);
    }

    /**
     * 接收from表单
     * @param pid
     * @param canvas
     * @param image
     * @param parent
     */
    @PostMapping("/insertVersion")
    @ApiOperation(value = "新建version",notes = "单面设计时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "项目id",required = true,dataType = DataType.INT),
            @ApiImplicitParam(name = "canvas", value = "服装纹样经过序列化后的json串",required = true,dataType = DataType.STRING),
            @ApiImplicitParam(name = "image", value = "衣服图片URL", required = true, dataType = DataType.STRING),
            @ApiImplicitParam(name = "parent", value = "上一版本version的id",required = true, dataType = DataType.INT)

    })
    public void insertVersion(Integer pid,String canvas,String image,Integer parent){
        colVersionService.insertVersion(pid, canvas, image, parent);
    }

    /**
     * 接收from表单,包括正面和背面
     * @param pid
     * @param canvas
     * @param image
     * @param parent
     */
    @PostMapping("/insertVersionDouble")
    @ApiOperation(value = "新建version",notes = "双面设计时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid",value = "项目id",required = true,dataType = DataType.INT),
            @ApiImplicitParam(name = "canvas", value = "正面服装纹样经过序列化后的json串",required = true,dataType = DataType.STRING),
            @ApiImplicitParam(name = "backCanvas", value = "背面服装纹样经过序列化后的json串",required = true,dataType = DataType.STRING),
            @ApiImplicitParam(name = "frontImage", value = "正面衣服图片URL", required = true, dataType = DataType.STRING),
            @ApiImplicitParam(name = "backImage", value = "背面衣服图片URL", required = true, dataType = DataType.STRING),
            @ApiImplicitParam(name = "parent", value = "上一版本version的id",required = true, dataType = DataType.INT)

    })
    public void insertVersionDouble(Integer pid,String canvas,String backCanvas,String frontImage,String backImage,Integer parent){
        colVersionService.insertVersionDouble(pid, canvas, backCanvas,frontImage,backImage, parent);
    }
}
