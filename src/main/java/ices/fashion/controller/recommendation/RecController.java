package ices.fashion.controller.recommendation;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.service.recommendation.RecService;
import ices.fashion.service.recommendation.dto.MaterialPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<MaterialPageDto> recommendations = recService.reqRecommendations(itemIds, matchType);
        resultMap.put("recommendations", recommendations);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
}
