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

public interface BrandService {
    /**
     * 品牌相关
     */
    List<TBaseMaterialBrand> selectAllBrands(Integer status);

    ApiResult deleteBrands(List<Integer> ids) throws Exception; // 软删除

    ApiResult recoverBrands(List<Integer> ids) throws Exception; // 撤销删除

    Integer saveBrand (TBaseMaterialBrand tBaseMaterialBrand, MultipartFile multipartFile); // 新增

    List<TBaseMaterialBrand> selectBrandByIds(List<Integer> idList, Integer status);
}
