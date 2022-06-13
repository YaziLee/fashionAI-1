package ices.fashion.controller.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.DataType;
import ices.fashion.constant.ParamType;
import ices.fashion.entity.space.TShare;
import ices.fashion.service.space.ShareService;
import ices.fashion.service.space.dto.ShowDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/share")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @GetMapping("/getUserShare")
    @ApiOperation(value = "拿到当前用户收藏别人的作品", notes = "id代表用户手机")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", dataType = DataType.STRING, paramType = ParamType.QUERY,
            value = "当前用户id")})
    public ApiResult<ShowDto> getUserShare(@RequestParam(value = "id") String id) {
        return shareService.getUserShare(id);
    }


    @PostMapping("/saveOneShare")
    @ApiOperation(value = "保存别人的作品到自己收藏", notes = "deleted, id字段不需要填写")
    @ApiImplicitParam(name = "share", value = "收藏对应内容", required = true, dataType = "TShare")
    public ApiResult<Integer> saveOneShare(@RequestBody TShare share) {
        return shareService.saveOneShare(share);
    }

    @DeleteMapping("/deleteOneShare/{id}")
    @ApiOperation(value = "删除一个自己收藏的作品", notes = "id为收藏对应的id")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", dataType = DataType.INT, paramType = ParamType.PATH,
            value = "收藏品对应id")})
    public ApiResult deleteOneShareById(@PathVariable Integer id) {
        return shareService.deleteOneShareById(id);
    }

}
