package ices.fashion.mapper.recommendation;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.recommendation.TBaseMaterialBrand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TBaseMaterialBrandMapper extends BaseMapper<TBaseMaterialBrand> {
    Integer deleteBrands(@Param("ids") List<Integer> ids);
    Integer recoverBrands(@Param("ids") List<Integer> ids);
    List<TBaseMaterialBrand> selectBrandByIds(@Param("ids") List<Integer> ids, @Param("status") Integer status);
    List<TBaseMaterialBrand> selectAllBrands(@Param("status") Integer status); // 当status不为空时，查询对应status；否则查询status=1

}
