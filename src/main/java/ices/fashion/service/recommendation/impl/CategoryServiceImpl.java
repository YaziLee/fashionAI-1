package ices.fashion.service.recommendation.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.recommendation.TBaseMaterialCategory;
import ices.fashion.mapper.recommendation.TBaseMaterialCategoryMapper;
import ices.fashion.service.recommendation.CategoryService;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
