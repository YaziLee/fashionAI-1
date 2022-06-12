package ices.fashion.service.impl.recommendation;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.storage.Api;
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
import ices.fashion.service.BrandService;
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
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TBaseMaterialBrandMapper tBaseMaterialBrandMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandServiceImpl.class);

    /*
查询所有状态为1的品牌
 */
    @Override
    public List<TBaseMaterialBrand> selectAllBrands(Integer status) {
        return tBaseMaterialBrandMapper.selectAllBrands(status);
//        return tBaseMaterialBrandMapper.selectByMap(conditions);
    }
    /*
    根据id列表查询品牌
     */
    @Override
    public List<TBaseMaterialBrand> selectBrandByIds(List<Integer> idList, Integer status){
        return tBaseMaterialBrandMapper.selectBrandByIds(idList, status);
    }
    /*
    根据id列表批量删除
     */
    public ApiResult deleteBrands(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialBrandMapper.deleteBrands(ids);
        if (status < 1)
                throw  new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }
    /*
    根据id列表批量撤销删除
     */
    @Override
    public ApiResult recoverBrands(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialBrandMapper.recoverBrands(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }
    @Override
    public Integer saveBrand (TBaseMaterialBrand tBaseMaterialBrand, MultipartFile multipartFile) {
        String imgUrl = FileUtil.uploadMultipartFile(multipartFile);
        tBaseMaterialBrand.setImgUrl(imgUrl);
        if(tBaseMaterialBrand.getId() == null) {
            // 新增
            tBaseMaterialBrandMapper.insert(tBaseMaterialBrand);
        } else {
            // 更新
            tBaseMaterialBrandMapper.updateById(tBaseMaterialBrand);
        }return tBaseMaterialBrand.getId(); }

}
