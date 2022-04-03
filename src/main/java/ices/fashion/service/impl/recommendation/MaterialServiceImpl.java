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
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Override
    public IPage<TBaseMaterial> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria) {
        // 查询对象
        QueryWrapper<TBaseMaterial> wrapper = new QueryWrapper<>();
        wrapper.eq("category_id", materialPageCriteria.getCategoryId());
        wrapper.in("brand_id", materialPageCriteria.getBrandIds());
        wrapper.eq("status", 1);
        // 分页 param1：第几页，param2：每页记录数
        Page<TBaseMaterial> page = new Page<>(materialPageCriteria.getCurrentPage(), materialPageCriteria.getPageSize());
        IPage<TBaseMaterial> iPage = tBaseMaterialMapper.selectPage(page, wrapper);
        return iPage;
    }

    @Override
    public List<TBaseMaterial> selectMaterialsByIds(List<Integer> idsList) {
        return tBaseMaterialMapper.selectBatchIds(idsList);
    }

}
