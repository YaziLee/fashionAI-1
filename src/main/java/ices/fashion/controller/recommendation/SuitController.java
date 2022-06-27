package ices.fashion.controller.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.recommendation.TBaseSuit;
import ices.fashion.service.recommendation.SuitService;
import ices.fashion.service.recommendation.dto.SuitAdminPageDto;
import ices.fashion.service.recommendation.dto.SuitAuditCriteria;
import ices.fashion.service.recommendation.dto.SuitPageCriteria;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/recommendation")
public class SuitController {
    @Autowired
    private SuitService suitService;

    /**
     * 根据id列表查询套装列表
     * @param ids
     * @param status
     * @return
     */
    @GetMapping("/get-suit-by-ids")
    @ApiOperation(value = "根据套装id列表查询套装记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "ids", value = "套装id列表", dataType = "STRING", allowMultiple = true, paramType = "QUERY", required = true), @ApiImplicitParam(name = "status", value = "套装状态", dataType = "INT", paramType = "QUERY")})
    public ApiResult<Map<String, Object>> getSuitByIds(@RequestParam("ids") List<Integer> ids, @RequestParam(required=false, name="status") Integer status) {
        Map<String, Object> resultMap = new HashMap<>(2);
        List<SuitAdminPageDto> suits = suitService.selectSuitsAdminDtoByIds(ids, status);
        resultMap.put("suits", suits);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    /**
     *
     * @param suitPageCriteria 查询条件
     * @return
     */
    @GetMapping("/get-suit-page")
    @ApiOperation(value = "根据用户手机号、套装状态、套装审核状态、当前页数、每页记录数查询套装记录")
    @ApiImplicitParams({@ApiImplicitParam(name = "suitPageCriteria", dataType = "SuitPageCriteria")})
    public ApiResult<IPage<SuitAdminPageDto>> getSuitPage(SuitPageCriteria suitPageCriteria) {
        Page<SuitAdminPageDto> suits = suitService.selectSuitsByPage(suitPageCriteria);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, suits);
    }

    /**
     * 更新套装记录（必须更新图片）或新增套装记录
     * @param request tBaseSuit
     * @return
     */
    @PostMapping("/save-suit")
    @ApiOperation(value = "新增或修改作品记录（包括图片）")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "name", value = "作品名称", dataType = "STRING", required = true, paramType = "form"),
                    @ApiImplicitParam(name = "materialIds", value = "作品使用到的素材id列表，用逗号分开",  dataType = "STRING", paramType = "form", required = true),
                    @ApiImplicitParam(name = "materialUrls", value = "作品使用到的素材图片链接列表，用逗号分开", dataType = "STRING", required = true, paramType = "form"),
                    @ApiImplicitParam(name = "description", value = "描述", dataType = "STRING", paramType = "form"),
                    @ApiImplicitParam(name = "customerId", value = "作品创作者电话号码", dataType = "STRING", paramType = "form", required = true),
                    @ApiImplicitParam(name = "price", value = "作品总价", dataType = "INT", paramType = "form", required = true),
                    @ApiImplicitParam(name = "status", value = "作品状态，0：已删除，1：草稿，2：私密，3：已发布", dataType = "INT", paramType = "form", required = true),
                    @ApiImplicitParam(name = "canvas", value = "画布信息", dataType = "STRING", paramType = "form"),
                    @ApiImplicitParam(name = "file", value = "作品画布图片", dataType = "__file", paramType = "form", required = true),
            }
    )
    public ApiResult<Map<String, Object>> saveSuit(MultipartHttpServletRequest request) {
        // 解析表单内容
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String materialIds = request.getParameter("materialIds");
        String materialUrls = request.getParameter("materialUrls");
        if (request.getParameter("customerId") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if (request.getParameter("price") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        if (request.getParameter("status") == null)
            return new ApiResult(ResultMessage.RESULT_ERROR_0);
        String customerId = request.getParameter("customerId").trim();
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        Integer status = Integer.valueOf(request.getParameter("status"));
        String canvas = request.getParameter("canvas");
        MultipartFile multipartFile = request.getFile("file");
        TBaseSuit savedSuit = suitService.insertSuit(name, description, materialIds, materialUrls, customerId, price, status, canvas, multipartFile);
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("savedSuit", savedSuit);
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

    /**
     * 更新作品文字信息和状态
     * @param request name, description, status
     * @return
     */
    @PostMapping("/update-suit")
    @ApiOperation(value = "更新作品记录的文字信息或状态")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "作品id", dataType = "INT", required = true, paramType = "form"),
                    @ApiImplicitParam(name = "name", value = "作品名称", dataType = "STRING", required = true, paramType = "form"),
                    @ApiImplicitParam(name = "description", value = "描述", dataType = "STRING", paramType = "form"),
                    @ApiImplicitParam(name = "status", value = "作品状态，0：已删除，1：草稿，2：私密，3：已发布", dataType = "INT", paramType = "form", required = true),
            }
    )
    public Integer updateSuit(MultipartHttpServletRequest request) {
        TBaseSuit tBaseSuit = new TBaseSuit();
        Integer id = Integer.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Integer status = Integer.valueOf(request.getParameter("status"));
        tBaseSuit.setId(id);
        tBaseSuit.setName(name);
        tBaseSuit.setDescription(description);
        tBaseSuit.setStatus(status);
        return suitService.updateSuit(tBaseSuit);
    }

    @PostMapping("/audit-suits")
    public ApiResult auditSuits(SuitAuditCriteria suitAuditCriteria) {
        return suitService.auditSuits(suitAuditCriteria);
    }
    /**
     * 软删除套装
     * @param ids
     * @return
     */
    @PostMapping("/delete-suits")
    @ApiOperation(value = "软删除套装")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "套装id列表", required = true, allowMultiple = true, dataType = "INT"),
            }
    )
    public ApiResult deleteSuits(@RequestParam("ids") List<Integer> ids) throws Exception{
        try {
            return suitService.deleteSuits(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }

    /**
     * 对于草稿是硬删除
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("/delete-drafts")
    @ApiOperation(value = "硬删除草稿作品，应验证是否为作品创作者所删除")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "套装id列表", required = true, allowMultiple = true, dataType = "INT"),
                    @ApiImplicitParam(name = "customerId", value = "用户手机号", required = true, dataType = "STRING")
            }
    )
    public ApiResult deleteDrafts(@RequestParam("ids") List<Integer> ids, @RequestParam("customerId") String customerId) throws Exception {
        try {
            return suitService.deleteDrafts(ids, customerId);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }
    @PostMapping("/recover-suits")
    @ApiOperation(value = "还原已删除套装")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "套装id列表", required = true, allowMultiple = true, dataType = "INT"),
            }
    )
    public ApiResult recoverSuits(@RequestParam("ids") List<Integer> ids) {
        try {
            return suitService.recoverSuits(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }

}
