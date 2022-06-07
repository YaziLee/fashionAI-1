package ices.fashion.controller.collaborate;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.collaborate.ColMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/collaborate")
public class ColMaterialController {

    @Autowired
    private ColMaterialService colMaterialService;

    @GetMapping("/getMaterialByCategory")
    public ApiResult<List<String>> getMaterialByCategory(@RequestParam String category) {
        return colMaterialService.getMaterialByCategory(category);
    }
}
