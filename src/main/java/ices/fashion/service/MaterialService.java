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

public interface MaterialService {
    /**
     * 搭配素材相关
     */
    IPage<TBaseMaterial> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria);

    List<TBaseMaterial> selectMaterialsByIds(List<Integer> idList);
}
