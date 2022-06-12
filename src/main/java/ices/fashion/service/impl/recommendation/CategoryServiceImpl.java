package ices.fashion.service.impl.recommendation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.entity.TBaseSuit;
import ices.fashion.mapper.TBaseMaterialBrandMapper;
import ices.fashion.mapper.TBaseMaterialCategoryMapper;
import ices.fashion.mapper.TBaseMaterialMapper;
import ices.fashion.mapper.TBaseSuitMapper;
import ices.fashion.service.CategoryService;
import ices.fashion.service.MaterialService;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private TBaseMaterialCategoryMapper tBaseMaterialCategoryMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public List<TBaseMaterialCategory> selectAllCategories(Integer status) {
        return tBaseMaterialCategoryMapper.selectAllCategories(status);
    }

    @Override
    public List<TBaseMaterialCategory> selectCategoriesByRecType(String recType) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("status", 1);
        conditions.put("rec_category", recType);
        return tBaseMaterialCategoryMapper.selectByMap(conditions);
    }
    @Override
    public ApiResult deleteCategories(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialCategoryMapper.deleteCategories(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }
    @Override
    public ApiResult recoverCategories(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialCategoryMapper.recoverCategories(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }
    @Override
    public Integer saveCategory (TBaseMaterialCategory tBaseMaterialCategory, MultipartFile multipartFile) {
        String imgUrl = FileUtil.uploadMultipartFile(multipartFile);
        tBaseMaterialCategory.setImgUrl(imgUrl);
        if (tBaseMaterialCategory.getId() == null) {
            tBaseMaterialCategoryMapper.insert(tBaseMaterialCategory);
        } else {
            tBaseMaterialCategoryMapper.updateById(tBaseMaterialCategory);
        }
        return tBaseMaterialCategory.getId();
    }
    @Override
    public List<TBaseMaterialCategory> selectCategoryByIds(List<Integer> idList, Integer status){
        return tBaseMaterialCategoryMapper.selectCategoryByIds(idList, status);
    }
}
