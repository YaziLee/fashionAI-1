package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.RecCriteria;
import ices.fashion.service.dto.RecDto;
import org.springframework.web.multipart.MultipartFile;
//import sun.nio.cs.ext.IBM037;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RecService {
    /**
     * 搭配推荐相关
     */
    List<TBaseMaterial> reqRecommendations(List<Integer> itemIds, String matchType)throws IOException;

    Map<Integer, String> selectRecCategoryMap();
}
