package ices.fashion.controller.recommendation;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qiniu.storage.Api;
import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.TBaseSuit;
import ices.fashion.service.SuitService;
import ices.fashion.service.dto.SuitAdminPageDto;
import ices.fashion.service.dto.SuitAuditCriteria;
import ices.fashion.service.dto.SuitPageCriteria;
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
    public ApiResult saveSuit(MultipartHttpServletRequest request) {
        // 解析表单内容
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String materialIds = request.getParameter("materialIds");
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
        return suitService.insertSuit(name, description, materialIds, customerId, price, status, canvas, multipartFile);
    }

    /**
     * 更新作品文字信息和状态
     * @param request name, description, status
     * @return
     */
    @PostMapping("/update-suit")
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
    public ApiResult deleteDrafts(@RequestParam("ids") List<Integer> ids, @RequestParam("customerId") String customerId) throws Exception {
        try {
            return suitService.deleteDrafts(ids, customerId);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }
    @PostMapping("/recover-suits")
    public ApiResult recoverSuits(@RequestParam("ids") List<Integer> ids) {
        try {
            return suitService.recoverSuits(ids);
        } catch (Exception e) {
            return new ApiResult(500, e.getMessage());
        }
    }

}
