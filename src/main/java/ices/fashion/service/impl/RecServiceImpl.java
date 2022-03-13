package ices.fashion.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import ices.fashion.service.RecService;
import ices.fashion.service.dto.RecDto;
import ices.fashion.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RecServiceImpl implements RecService {
    @Autowired
    private TBaseMaterialBrandMapper tBaseMaterialBrandMapper;
    @Autowired
    private TBaseMaterialCategoryMapper tBaseMaterialCategoryMapper;
    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;
    @Autowired
    private TBaseSuitMapper tBaseSuitMapper;
    @Autowired
    private FileUtil fileUtil;
    /*
    查询所有状态为1的品牌
     */
    @Override
    public List<TBaseMaterialBrand> selectAllBrands() {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("status", 1);
        return tBaseMaterialBrandMapper.selectByMap(conditions);
    }

    @Override
    public List<TBaseMaterialCategory> selectAllCategories() {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("status", 1);
        return tBaseMaterialCategoryMapper.selectByMap(conditions);
    }

    @Override
    public IPage<TBaseMaterial> selectMaterialsByPage(Integer categoryId, Integer[] brandIds, Integer currentPage, Integer pageSize) {
        // 查询对象
        QueryWrapper<TBaseMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", categoryId);
        wrapper.in("brand_id", brandIds);
        wrapper.eq("status", 1);
        // 分页 param1：第几页，param2：每页记录数
        Page<TBaseMaterial> page = new Page<>(currentPage, pageSize);
        IPage<TBaseMaterial> iPage = tBaseMaterialMapper.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public List<TBaseMaterial> selectMaterialsByIds(List<Integer> idsList) {
        return tBaseMaterialMapper.selectBatchIds(idsList);
    }

    @Override
    public ApiResult insertSuit(String name, String description, String materialIds, Integer customerId, BigDecimal price, Integer status, String imgUrl) {
        TBaseSuit tBaseSuit = new TBaseSuit();
        tBaseSuit.setCustomerId(customerId);
        tBaseSuit.setName(name);
        tBaseSuit.setMaterialIds(materialIds);
        tBaseSuit.setImgUrl(imgUrl);
        tBaseSuit.setStatus(status);
        tBaseSuit.setAuditStatus(0);
        tBaseSuit.setPrice(price);
        tBaseSuit.setDescription(description);
        tBaseSuitMapper.insert(tBaseSuit);
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public List<RecDto> reqRecommendations(List<Integer> idsList, String matchType) {
        List<TBaseMaterial> materials = selectMaterialsByIds(idsList);
        List<String> urlList = materials.stream().map(TBaseMaterial::getImgUrl).collect(Collectors.toList());
    }

}
