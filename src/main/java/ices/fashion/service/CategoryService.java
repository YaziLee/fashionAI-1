package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.dto.MaterialPageCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface CategoryService {
    /**
     * 搭配素材种类相关
     */
    List<TBaseMaterialCategory> selectAllCategories(Integer status);

    List<TBaseMaterialCategory> selectCategoriesByRecType(String recType);

    ApiResult deleteCategories(List<Integer> ids) throws Exception; // 软删除

    ApiResult recoverCategories(List<Integer> ids) throws Exception; // 撤销删除

    Integer saveCategory (TBaseMaterialCategory tBaseMaterialCategory, MultipartFile multipartFile);

    List<TBaseMaterialCategory> selectCategoryByIds(List<Integer> idList, Integer status);
}
