package ices.fashion.service.recommendation;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.recommendation.TBaseMaterialBrand;
import org.springframework.web.multipart.MultipartFile;

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
