package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.RecCriteria;
import ices.fashion.service.dto.RecDto;
import javafx.scene.paint.Material;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.cs.ext.IBM037;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RecService {
    List<TBaseMaterialBrand> selectAllBrands();

    List<TBaseMaterialCategory> selectAllCategories();

    IPage<TBaseMaterial> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria);
//    IPage<TBaseMaterial> selectMaterialsByPage(Integer categoryId, List<Integer> brandIds, Integer currentPage, Integer pageSize);

    List<TBaseMaterial> selectMaterialsByIds(List<Integer> idList);

    ApiResult insertSuit(String name, String description, String materialIds, Integer customerId, BigDecimal price, Integer status, MultipartFile multipartFile);

    List<TBaseMaterial> reqRecommendations(List<Integer> itemIds, String matchType)throws IOException;

    Map<Integer, String> selectRecCategoryMap();
}
