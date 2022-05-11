package ices.fashion.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.MaterialPageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MaterialService {
    /**
     * 搭配素材相关
     */
    Page<MaterialPageDto> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria); // 分页查询

    List<TBaseMaterial> selectMaterialsByIds(List<Integer> idList);

    List<MaterialPageDto> selectMaterialDtoByIds(List<Integer> idList, Integer status);

    // 根据id列表软删除素材记录
    ApiResult deleteMaterials(List<Integer> ids) throws Exception;

    ApiResult recoverMaterials(List<Integer> ids) throws Exception; // 撤销删除

    Integer saveMaterial(TBaseMaterial tBaseMaterial, MultipartFile multipartFile); // 新增或修改，返回id
}
