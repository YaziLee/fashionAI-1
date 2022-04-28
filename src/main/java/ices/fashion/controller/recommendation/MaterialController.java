package ices.fashion.controller.recommendation;

import ch.qos.logback.core.encoder.EchoEncoder;
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

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    /*
    获取素材分页列表
     */
    @GetMapping("/get-material-page")
    public ApiResult<IPage<TBaseMaterial>> getMaterialPage(MaterialPageCriteria materialPageCriteria) {
        IPage<TBaseMaterial> materials = materialService.selectMaterialsByPage(materialPageCriteria);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, materials);
    }
    /*
    批量删除素材
     */
    @PostMapping("/delete-materials")
    public ApiResult deleteMaterials(List<Integer> ids) {
        try {
            return materialService.deleteMaterials(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }

    /*
    批量撤销删除
     */
    @PostMapping("/recover-materials")
    public ApiResult recoverMaterials(List<Integer> ids) throws Exception {
        try {
            return materialService.recoverMaterials(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }

    /*
    保存素材
     */
    @PostMapping("/save-material")
    public ApiResult saveMaterial(MultipartHttpServletRequest request) {
        // 解析表单内容
        TBaseMaterial tBaseMaterial = new TBaseMaterial();
        tBaseMaterial.setCategoryId(Integer.valueOf(request.getParameter("categoryId")));
        tBaseMaterial.setBrandId(Integer.valueOf(request.getParameter("brandId")));
        tBaseMaterial.setLinkUrl(request.getParameter("linkUrl"));
        tBaseMaterial.setPrice(new BigDecimal(request.getParameter("price")));
        tBaseMaterial.setStatus(1);
        tBaseMaterial.setDescription(request.getParameter("description"));
        tBaseMaterial.setTargetPopulation(request.getParameter("targetPopulation"));
        MultipartFile multipartFile = request.getFile("file");
        return materialService.saveMaterial(tBaseMaterial, multipartFile);
    }
}
