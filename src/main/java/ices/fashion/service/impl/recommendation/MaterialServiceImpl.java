package ices.fashion.service.impl.recommendation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.mapper.TBaseMaterialMapper;
import ices.fashion.service.MaterialService;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.MaterialPageDto;
import ices.fashion.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private TBaseMaterialMapper tBaseMaterialMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialServiceImpl.class);

    @Override
    public Page<MaterialPageDto> selectMaterialsByPage(MaterialPageCriteria materialPageCriteria) {
        int currentPage = materialPageCriteria.getCurrentPage();
        int pageSize =  materialPageCriteria.getPageSize();
        Page<MaterialPageDto> page = new Page<>(currentPage, pageSize);
        return page.setRecords(this.tBaseMaterialMapper.selectMaterialPage(page, materialPageCriteria));
//        List<Object> objects = this.tBaseMaterialMapper.selectMaterialPage(page, materialPageCriteria); // 0：dto，1：总记录数
//        List<MaterialPageDto> materialPageDtoList = (List<MaterialPageDto>) objects.get(0);
//        int total = ((List<Integer>) objects.get(1)).get(0);
//        page.setRecords(materialPageDtoList);
//        page.setTotal(total);
//        page.setPages(total % pageSize == 0? total/pageSize : total/pageSize+1);
//        return page;
    }

    @Override
    public List<TBaseMaterial> selectMaterialsByIds(List<Integer> idsList) {
        return tBaseMaterialMapper.selectBatchIds(idsList);
    }

    @Override
    public List<MaterialPageDto> selectMaterialDtoByIds(List<Integer> idList, Integer status) {
        return tBaseMaterialMapper.selectMaterialDtoByIds(idList, status);
    }

    @Override
    public ApiResult deleteMaterials(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialMapper.deleteMaterials(ids);
        if (status < 1)
            throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public ApiResult recoverMaterials(List<Integer> ids) throws Exception {
        Integer status = tBaseMaterialMapper.recoverMaterials(ids);
        if (status < 1)
                throw new Exception();
        return new ApiResult(ResultMessage.RESULT_SUCCESS_1);
    }

    @Override
    public Integer saveMaterial(TBaseMaterial tBaseMaterial, MultipartFile multipartFile) {
        // 上传图片
        String imgUrl = FileUtil.uploadMultipartFile(multipartFile);
        tBaseMaterial.setImgUrl(imgUrl);
        if (tBaseMaterial.getId() == null) {
            // 新增
            tBaseMaterialMapper.insert(tBaseMaterial);
        } else {
            // 修改
            tBaseMaterialMapper.updateById(tBaseMaterial);
        }
        return tBaseMaterial.getId();
    }
}
