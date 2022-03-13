package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.dto.RecDto;
import sun.nio.cs.ext.IBM037;

import java.math.BigDecimal;
import java.util.List;

public interface RecService {
    List<TBaseMaterialBrand> selectAllBrands();

    List<TBaseMaterialCategory> selectAllCategories();

    IPage<TBaseMaterial> selectMaterialsByPage(Integer categoryId, Integer[] brandIds, Integer currentPage, Integer pageSize);

    List<TBaseMaterial> selectMaterialsByIds(List<Integer> idList);

    ApiResult insertSuit(String name, String description, String materialIds, Integer customerId, BigDecimal price, Integer status, String imgUrl);

    List<RecDto> reqRecommendations(List<Integer> idsList, String matchType);
}
