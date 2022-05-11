package ices.fashion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.TBaseMaterialCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TBaseMaterialCategoryMapper extends BaseMapper<TBaseMaterialCategory> {
    Integer deleteCategories(@Param("ids")List<Integer> ids);
    Integer recoverCategories(@Param("ids") List<Integer> ids);
    List<TBaseMaterialCategory> selectCategoryByIds(@Param("ids") List<Integer> ids, @Param("status") Integer status);
    List<TBaseMaterialCategory> selectAllCategories(@Param("status") Integer status);// 当status不为空时，查询对应status；否则查询status=1
}
