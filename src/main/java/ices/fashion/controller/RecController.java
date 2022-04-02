package ices.fashion.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonArray;
import com.qiniu.storage.Api;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.RecService;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
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
    public ApiResult<IPage<TBaseMaterial>> getMaterialPage(MaterialPageCriteria materialPageCriteria) {
        IPage<TBaseMaterial> materials = recService.selectMaterialsByPage(materialPageCriteria);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, materials);
    }

    @PostMapping("/save-suit")
    public ApiResult saveSuit(MultipartHttpServletRequest request) {
        // 解析表单内容
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String materialIds = request.getParameter("materialIds");
        if (request.getParameter("customerId") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if (request.getParameter("price") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if (request.getParameter("status") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        Integer customerId = Integer.valueOf(request.getParameter("customerId"));
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Integer status = Integer.valueOf(request.getParameter("status"));
        MultipartFile multipartFile = request.getFile("file");
        return recService.insertSuit(name, description, materialIds, customerId, price, status, multipartFile);
    }

    @GetMapping("get-recommendation-list")
    public ApiResult<Map<String, Object>> getRecommendationList(@RequestParam("matchType") String matchType, @RequestParam("itemIds") List<Integer> itemIds) throws IOException {
        if (itemIds.size() != 3)
            return new ApiResult<>(ResultMessage.RESULT_ERROR_0);
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterial> recommendations = recService.reqRecommendations(itemIds, matchType);
        resultMap.put("recommendations", recommendations);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    @GetMapping("get-rec-categories")
    public ApiResult<Map<String, Object>> getRecCategories() {
        Map<String, Object> resultMap = new HashMap<>(2);
        Map<Integer, String> recCategoryMap = recService.selectRecCategoryMap();
        resultMap.put("recCategoryMap", recCategoryMap);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
}
