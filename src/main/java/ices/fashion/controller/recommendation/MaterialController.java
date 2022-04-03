package ices.fashion.controller.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.MaterialService;
import ices.fashion.service.dto.MaterialPageCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @GetMapping("/get-material-page")
    public ApiResult<IPage<TBaseMaterial>> getMaterialPage(MaterialPageCriteria materialPageCriteria) {
        IPage<TBaseMaterial> materials = materialService.selectMaterialsByPage(materialPageCriteria);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, materials);
    }
}
