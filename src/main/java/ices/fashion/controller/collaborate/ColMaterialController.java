package ices.fashion.controller.collaborate;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.DataType;
import ices.fashion.constant.ParamType;
import ices.fashion.service.collaborate.ColMaterialService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class ColMaterialController {

    @Autowired
    private ColMaterialService colMaterialService;

    @GetMapping("/getMaterialByCategory")
    @ApiOperation(value = "拿到协同设计相关的素材", notes = "category代表素材的种类（template、icons……")
    @ApiImplicitParams({@ApiImplicitParam(name = "category", dataType = DataType.STRING, paramType = ParamType.QUERY,
            value = "素材种类")})
    public ApiResult<List<String>> getMaterialByCategory(@RequestParam String category) {
        return colMaterialService.getMaterialByCategory(category);
    }
}
