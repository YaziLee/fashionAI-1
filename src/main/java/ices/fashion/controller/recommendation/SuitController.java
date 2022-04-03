package ices.fashion.controller.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.MaterialService;
import ices.fashion.service.SuitService;
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
public class SuitController {
    @Autowired
    private SuitService suitService;

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
        return suitService.insertSuit(name, description, materialIds, customerId, price, status, multipartFile);
    }

}
