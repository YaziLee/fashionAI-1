package ices.fashion.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TBaseMaterial;
import ices.fashion.entity.TBaseMaterialBrand;
import ices.fashion.entity.TBaseMaterialCategory;
import ices.fashion.entity.TBaseSuit;
import ices.fashion.service.dto.MaterialPageCriteria;
import ices.fashion.service.dto.SuitAdminPageDto;
import ices.fashion.service.dto.SuitAuditCriteria;
import ices.fashion.service.dto.SuitPageCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface SuitService {
    /**
     * 搭配套装相关
     */
    // 更新套装记录（包括图片）或新增记录
    ApiResult insertSuit(String name, String description, String materialIds, String customerId, BigDecimal price, Integer status, String canvas, MultipartFile multipartFile);

    // 更新套装记录文本信息和状态
    Integer updateSuit(TBaseSuit tBaseSuit);

    // 根据查询条件返回套装记录页
    Page<SuitAdminPageDto> selectSuitsByPage(SuitPageCriteria suitPageCriteria);

    // 根据id列表查询套装管理信息记录列表
    List<SuitAdminPageDto> selectSuitsAdminDtoByIds(List<Integer> idList, Integer status);

    // 根据id列表软删除套装记录
    ApiResult deleteSuits(List<Integer> ids) throws Exception;

    // 根据id列表还原删除套装记录到私密状态（status= 2）
    ApiResult recoverSuits(List<Integer> ids) throws Exception;

    // 审核套装，允许批量审核；批量审核时无审核评语
    ApiResult auditSuits(SuitAuditCriteria suitAuditCriteria);

    // 根据id列表硬删除草稿记录
    ApiResult deleteDrafts(List<Integer> ids, String customerId) throws Exception;
}
