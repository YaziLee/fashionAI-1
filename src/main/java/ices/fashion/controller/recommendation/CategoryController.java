package ices.fashion.controller.recommendation;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.recommendation.TBaseMaterialCategory;
import ices.fashion.service.recommendation.CategoryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/get-category-list")
    @ApiOperation(value = "根据种类状态status返回相应的品牌记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "status", value = "种类状态", dataType = "INT", paramType = "QUERY", required = true)})
    public ApiResult<Map<String, Object>> getCategoryList(@RequestParam(required=false, name="status") Integer status) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialCategory> categories = categoryService.selectAllCategories(status);
        resultMap.put("categories", categories);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
    @GetMapping("/get-category-by-rec-type")
    @ApiOperation(value = "根据大分类（上衣，下衣，包，鞋）查询种类记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "recType", value = "大分类", dataType = "STRING", paramType = "QUERY", required = true)})
    public ApiResult<Map<String, Object>> getCategoryByRecType(@RequestParam("recType") String recType) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialCategory> categories = categoryService.selectCategoriesByRecType(recType);
        resultMap.put("categories", categories);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
    @GetMapping("get-category-by-ids")
    @ApiOperation(value = "根据种类id列表查询种类记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "种类id列表", dataType = "STRING", allowMultiple = true, paramType = "QUERY", required = true), @ApiImplicitParam(name = "status", value = "种类状态", dataType = "INT", paramType = "QUERY")})
    public ApiResult<Map<String, Object>> getCategoryByIds(@RequestParam("ids") List<Integer> ids, @RequestParam(required=false, name="status") Integer status) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<TBaseMaterialCategory> categories = categoryService.selectCategoryByIds(ids, status);
        resultMap.put("categories", categories);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
    /**
     * 批量删除分类
     * NOTES：删除分类后，其下所有商品将不会被selectMaterialsByPage查询得到，因为该查询函数要求素材、品牌、种类的status=1
     */
    @PostMapping("delete-categories")
    @ApiOperation(value = "软删除种类")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "种类id列表", required = true, allowMultiple = true, dataType = "INT"),
            }
    )
    public ApiResult deleteBrands(@RequestParam("ids") List<Integer> ids) throws Exception {
        try {
            return categoryService.deleteCategories(ids);
        } catch (Exception e) {
            return new ApiResult(50, e.getMessage());
        }
    }
        /*
    批量撤销删除
     */
    @PostMapping("/recover-categories")
    @ApiOperation(value = "还原已删除种类")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "种类id列表", required = true, allowMultiple = true, dataType = "INT"),
            }
    )
    public ApiResult recoverCategories(@RequestParam("ids") List<Integer> ids) throws Exception {
        try {
            return categoryService.recoverCategories(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }
    /**
     * 保存品牌
     */
    @PostMapping("/save-category")
    @ApiOperation(value = "新增或者修改种类记录", notes = "新增或者修改种类记录")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id", value = "id", dataType = "INT", paramType = "form"),
                    @ApiImplicitParam(name = "categoryName", value = "名称", dataType = "STRING", required = true, paramType = "form"),
                    @ApiImplicitParam(name = "description", value = "描述", dataType = "STRING", paramType = "form"),
                    @ApiImplicitParam(name = "recCategory", value = "所属大类（上衣、下衣、包、鞋、其他）", dataType = "STRING", paramType = "form", required = true),
                    @ApiImplicitParam(name = "file", value = "图片", dataType = "__file", paramType = "form", required = true),
            }
    )
    public ApiResult<Map<String, Object>> saveCategory(MultipartHttpServletRequest request) {
        TBaseMaterialCategory tBaseMaterialCategory = new TBaseMaterialCategory();
        if(request.getParameter("id") != null && !request.getParameter("id").equals("null") && !request.getParameter("id").equals("") && !request.getParameter("id").isEmpty()) {
            tBaseMaterialCategory.setId(Integer.valueOf(request.getParameter("id")));
        }
        tBaseMaterialCategory.setCategoryName(request.getParameter("categoryName"));
        if (!request.getParameter("description").equals("null") && !request.getParameter("description").isEmpty()) {
            // 如果不判断，空描述会被读为“null”
            tBaseMaterialCategory.setDescription(request.getParameter("description"));
        }
        tBaseMaterialCategory.setRecCategory(request.getParameter("recCategory"));
        tBaseMaterialCategory.setStatus(1);
        MultipartFile multipartFile = request.getFile("file");
        Integer id = categoryService.saveCategory(tBaseMaterialCategory, multipartFile);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("id", id);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }
}
