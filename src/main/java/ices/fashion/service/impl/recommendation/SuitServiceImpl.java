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
import ices.fashion.service.SuitService;
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
public class SuitServiceImpl implements SuitService {
    @Autowired
    private TBaseSuitMapper tBaseSuitMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SuitServiceImpl.class);

    @Override
    public ApiResult insertSuit(String name, String description, String materialIds, Integer customerId, BigDecimal price, Integer status, MultipartFile multipartFile) {
        String imgUrl = FileUtil.uploadMultipartFile(multipartFile); // 上传图片
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
}
