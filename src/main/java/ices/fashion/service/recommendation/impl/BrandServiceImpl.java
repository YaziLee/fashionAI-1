package ices.fashion.service.recommendation.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.recommendation.TBaseMaterialBrand;
import ices.fashion.mapper.recommendation.TBaseMaterialBrandMapper;
import ices.fashion.service.recommendation.BrandService;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
