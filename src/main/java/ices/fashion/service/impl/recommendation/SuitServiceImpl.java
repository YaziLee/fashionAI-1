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
import ices.fashion.service.dto.*;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuitServiceImpl implements SuitService {
    @Autowired
    private TBaseSuitMapper tBaseSuitMapper;
    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(SuitServiceImpl.class);

    @Override
    public ApiResult insertSuit(String name, String description, String materialIds, String customerId, BigDecimal price, Integer status, String canvas, MultipartFile multipartFile) {
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
        tBaseSuit.setCanvas(canvas);
        tBaseSuitMapper.insert(tBaseSuit);
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public Integer updateSuit(TBaseSuit tBaseSuit){
        tBaseSuitMapper.updateById(tBaseSuit);
        return tBaseSuit.getId();
    }

    @Override
    public Page<SuitAdminPageDto> selectSuitsByPage(SuitPageCriteria suitPageCriteria){
        int currentPage = suitPageCriteria.getCurrentPage();
        int pageSize = suitPageCriteria.getPageSize();
        Page<SuitAdminPageDto> page = new Page<>(currentPage, pageSize);
        return page.setRecords(this.tBaseSuitMapper.selectSuitPage(page, suitPageCriteria));
    }

    @Override
    public List<SuitAdminPageDto> selectSuitsAdminDtoByIds(List<Integer> idList, Integer status) {
        return tBaseSuitMapper.selectSuitsAdminDtoByIds(idList, status);
    }

    @Override
    public  ApiResult deleteSuits(List<Integer> ids) throws Exception {
        Integer status = tBaseSuitMapper.deleteSuits(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public ApiResult recoverSuits(List<Integer> ids) throws Exception {
        Integer status = tBaseSuitMapper.recoverSuits(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }


    @Override
    public ApiResult auditSuits(SuitAuditCriteria suitAuditCriteria){
        tBaseSuitMapper.auditSuits(suitAuditCriteria);
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public ApiResult deleteDrafts(List<Integer> ids, String customerId) throws Exception {
        Integer status = tBaseSuitMapper.deleteDraftsByIds(ids, customerId);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }
}
