package ices.fashion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.TBaseMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface TBaseMaterialMapper extends BaseMapper<TBaseMaterial> {
    Integer deleteMaterials(@Param("ids") List<Integer> ids);
    Integer recoverMaterials(@Param("ids") List<Integer> ids);
}
