package ices.fashion.service.collaborate.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.collaborate.TCollaborateMaterial;
import ices.fashion.mapper.collaborate.CollaborateMaterialMapper;
import ices.fashion.service.collaborate.ColMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColMaterialServiceImpl implements ColMaterialService {

    @Autowired
    private CollaborateMaterialMapper collaborateMaterialMapper;


    @Override
    public ApiResult<List<String>> getMaterialByCategory(String category) {
        QueryWrapper<TCollaborateMaterial> collaborateMaterialQueryWrapper = new QueryWrapper<>();
        collaborateMaterialQueryWrapper.eq("category", category)
                .select("file_name").eq("deleted", 0);
        List<TCollaborateMaterial> materialList = collaborateMaterialMapper.selectList(collaborateMaterialQueryWrapper);
        ApiResult<List<String>> res = new ApiResult<>(200, "success");
        res.setData(materialList.stream().map(TCollaborateMaterial::getFileName).collect(Collectors.toList()));
        return res;
    }
}
