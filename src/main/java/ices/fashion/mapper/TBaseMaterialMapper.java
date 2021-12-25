package ices.fashion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.TBaseMaterial;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface TBaseMaterialMapper extends BaseMapper<TBaseMaterial> {
}
