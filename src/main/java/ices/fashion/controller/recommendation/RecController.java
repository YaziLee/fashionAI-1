package ices.fashion.controller.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.JsonArray;
import com.qiniu.storage.Api;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.MaterialService;
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
