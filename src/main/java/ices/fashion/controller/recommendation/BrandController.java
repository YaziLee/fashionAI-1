package ices.fashion.controller.recommendation;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.recommendation.TBaseMaterialBrand;
import ices.fashion.service.recommendation.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/get-brand-list")
    public ApiResult<Map<String, Object>> getBrandList(@RequestParam(required=false, name="status") Integer status) {
        Map<String, Object> resultMap = new HashMap<>(2);
        status = status == null? 1: status;
        List<TBaseMaterialBrand> brands = brandService.selectAllBrands(status);
        resultMap.put("brands", brands);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    @GetMapping("get-brand-by-ids")
    public ApiResult<Map<String, Object>> getBrandByIds(@RequestParam("ids") List<Integer> ids, @RequestParam(required=false, name="status") Integer status) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialBrand> brands = brandService.selectBrandByIds(ids, status);
        resultMap.put("brands", brands);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
    /**
     * 批量删除品牌
     * NOTES：删除品牌后，其下所有商品将不会被selectMaterialsByPage查询得到，因为该查询函数要求素材、品牌、种类的status=1
     */
    @PostMapping("delete-brands")
    public ApiResult deleteBrands(@RequestParam("ids") List<Integer> ids) throws Exception {
        try {
            return brandService.deleteBrands(ids);
        } catch (Exception e) {
            return new ApiResult(50, e.getMessage());
        }
    }
    /*
    批量撤销删除
     */
    @PostMapping("/recover-brands")
    public ApiResult recoverBrands(@RequestParam("ids") List<Integer> ids) throws Exception {
        try {
            return brandService.recoverBrands(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }
    /**
     * 保存品牌
     */
    @PostMapping("/save-brand")
    public ApiResult<Map<String, Object>> saveBrand(MultipartHttpServletRequest request) {
        TBaseMaterialBrand tBaseMaterialBrand = new TBaseMaterialBrand();
        if(!request.getParameter("id").equals("null") && !request.getParameter("id").isEmpty()) {
            tBaseMaterialBrand.setId(Integer.valueOf(request.getParameter("id")));
        }
        tBaseMaterialBrand.setBrandName(request.getParameter("brandName"));
        tBaseMaterialBrand.setDescription(request.getParameter("description"));
        tBaseMaterialBrand.setStatus(1);
        MultipartFile multipartFile = request.getFile("file");
        Integer id = brandService.saveBrand(tBaseMaterialBrand, multipartFile);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("id", id);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
}
