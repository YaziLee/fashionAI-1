package ices.fashion.controller.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.DataType;
import ices.fashion.constant.ParamType;
import ices.fashion.entity.space.TWork;
import ices.fashion.service.space.WorkService;
import ices.fashion.service.space.dto.ShareWorkCriteria;
import ices.fashion.service.space.dto.ShowDto;
import ices.fashion.service.space.dto.WorkDetailDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/work")
public class WorkController {

    @Autowired
    private WorkService workService;

    /*
    id是phone
     */
    @ApiOperation(value = "得到用户本人设计并保存的作品", notes = "返回所有种类对应的dto的list，游客只能看到当前用户分享的作品")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户id目前是手机号", dataType = DataType.STRING,
            paramType = ParamType.QUERY), @ApiImplicitParam(name = "isVisitor", value = "是否为游客",
            dataType = DataType.BOOLEAN, paramType = ParamType.QUERY)})
    @GetMapping("/getUserDesign")
    public ApiResult<ShowDto> getUserDesign(@RequestParam(value ="id") String id,
                                            @RequestParam(value = "isVisitor") Boolean isVisitor) {
        return workService.getUserDesign(id, isVisitor);
    }

    /*
    id是phone
     */
    @GetMapping("/getWorkDetail")
    @ApiOperation(value = "拿到作品详细信息", notes = "返回作品详细信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "wid", value = "work对应id", dataType = DataType.INT,
            paramType = ParamType.QUERY), @ApiImplicitParam(name = "uid", value = "访问这个详细信息的用户id，目前是手机号",
            dataType = DataType.STRING, paramType = ParamType.QUERY)})
    public ApiResult<WorkDetailDto> getWorkDetail(@RequestParam(value = "wid") Integer wid,
                                                  @RequestParam(value = "uid") String uid) {
        return workService.getWorkDetail(wid, uid);
    }

    @PostMapping("/saveOneWork")
    @ApiOperation(value = "保存一个作品", notes = "id, 两个time, deleted和shared不用传参，剩下需要")
    @ApiImplicitParam(name = "work", value = "作品内容", dataType = "TWork",
            required = true)
    public ApiResult saveOneWork(@RequestBody TWork work) {
        return workService.saveOneWork(work);
    }

    @PostMapping("/shareWork")
    @ApiOperation(value = "将这个作品分享", notes = "返回所有种类对应的dto的list, 游客只能看到当前用户分享的作品")
    @ApiImplicitParam(name = "shareWorkCriteria", value = "被分享作品的一些信息", dataType = "ShareWorkCriteria",
            required = true)
    public ApiResult shareWork(@RequestBody ShareWorkCriteria shareWorkCriteria) {
        return workService.shareWork(shareWorkCriteria);
    }

    @GetMapping("/getAllShareWork")
    @ApiOperation(value = "得到所有用户分享的作品", notes = "")
    public ApiResult<ShowDto> getAllShareWork() {
        return workService.getAllShareWork();
    }

    @DeleteMapping("/cancelOneWorkShare/{wid}")
    @ApiOperation(value = "用户取消某个作品的分享", notes = "")
    @ApiImplicitParam(name = "wid", value = "作品对应id",
            dataType = DataType.INT, paramType = ParamType.PATH)
    public ApiResult cancelOneWorkShare(@PathVariable Integer wid) {
        return workService.cancelOneWorkShare(wid);
    }
}
