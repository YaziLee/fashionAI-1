package ices.fashion.mapper.recommendation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ices.fashion.entity.recommendation.TBaseSuit;
import ices.fashion.service.recommendation.dto.SuitAdminPageDto;
import ices.fashion.service.recommendation.dto.SuitAuditCriteria;
import ices.fashion.service.recommendation.dto.SuitPageCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TBaseSuitMapper extends BaseMapper<TBaseSuit> {
    // 根据传入的查询条件查询套装页面
    List<SuitAdminPageDto> selectSuitPage(Page page, SuitPageCriteria suitPageCriteria);
    // 根据传入的查询条件查询套装管理记录
    List<SuitAdminPageDto> selectSuitsAdminDtoByIds(@Param("ids") List<Integer> ids, @Param("status") Integer status);
    // 批量软删除
    Integer deleteSuits(@Param("ids") List<Integer> ids);
    // 批量恢复
    Integer recoverSuits(@Param("ids") List<Integer> ids);
    // 批量审核
    void auditSuits(@Param("suitAuditCriteria") SuitAuditCriteria suitAuditCriteria);
    // 硬删除草稿，应该检查是否为草稿和是否为创作者
    Integer deleteDraftsByIds(@Param("ids") List<Integer> ids, @Param("customerId") String customerId);


}


