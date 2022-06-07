package ices.fashion.service.collaborate;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.collaborate.TCollaborateMaterial;

import java.util.List;

public interface ColMaterialService {

    public ApiResult<List<String>> getMaterialByCategory(String category);
}
