package ices.fashion.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonArray;
import com.qiniu.storage.Api;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.entity.TBaseSuit;
import ices.fashion.service.RecService;
import ices.fashion.service.dto.RecDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class RecController {

    @Autowired
    private RecService recService;
    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/get-brand-list")
    public ApiResult<Map<String, Object>> getBrandList() {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialBrand> brands = recService.selectAllBrands();
        resultMap.put("brands", brands);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    @GetMapping("/get-category-list")
    public ApiResult<Map<String, Object>> getCategoryList() {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialCategory> categories = recService.selectAllCategories();
        resultMap.put("categories", categories);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    @GetMapping("/get-material-page")
    public ApiResult<IPage<TBaseMaterial>> getMaterialPage(@RequestParam("categoryId") Integer categoryId, @RequestParam("brandIds") Integer[] brandIds, @RequestParam("currentPage") Integer currentPage, @RequestParam("pageSize") Integer pageSize) {
        IPage<TBaseMaterial> materials = recService.selectMaterialsByPage(categoryId, brandIds, currentPage, pageSize);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, materials);
    }
    @PostMapping("/save-suit")
    public ApiResult saveSuit(MultipartHttpServletRequest request) {
        // 解析表单内容
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String materialIds = request.getParameter("materialIds");
        if(request.getParameter("customerId") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if(request.getParameter("price") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if(request.getParameter("status") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        Integer customerId = Integer.valueOf(request.getParameter("customerId"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Integer status = Integer.valueOf(request.getParameter("status"));
        MultipartFile multipartFile = request.getFile("file");
        String imgUrl = fileUtil.uploadMultipartFile(multipartFile); // 上传图片
        return recService.insertSuit(name, description, materialIds, customerId, price, status, imgUrl);
    }
    @GetMapping("get-recommendation-list")
    public ApiResult<Map<String, Object>> getRecommendationList(@RequestParam("upper") Integer upperId, @RequestParam("bottom") Integer bottomId, @RequestParam("bag") Integer bagId, @RequestParam("shoe") Integer shoeId, @RequestParam("matchType") String matchType) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<Integer> idsList = new ArrayList<>();
        if (upperId != null)
            idsList.add(upperId);
        if (bottomId != null)
            idsList.add(bottomId);
        if (bagId != null)
            idsList.add(bagId);
        if (shoeId != null)
            idsList.add(shoeId);
        if (idsList.size() != 3) // 必须有三个输入
            return new ApiResult<>(ResultMessage.RESULT_ERROR_0, resultMap);
        List<RecDto> recommendations = recService.reqRecommendations(idsList, matchType);
        resultMap.put("recommendations", recommendations);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
}
