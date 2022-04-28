package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qiniu.storage.Api;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.service.dto.MaterialPageCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface MaterialService {
    /**
     * 搭配素材相关
     */
    IPage<TBaseMaterial> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria); // 分页查询

    List<TBaseMaterial> selectMaterialsByIds(List<Integer> idList);

    ApiResult deleteMaterials(List<Integer> ids) throws Exception; // 软删除

    ApiResult recoverMaterials(List<Integer> ids) throws Exception; // 撤销删除

    ApiResult saveMaterial(TBaseMaterial tBaseMaterial, MultipartFile multipartFile); // 新增或修改
}
