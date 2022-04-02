package ices.fashion.mapper.collaborate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.collaborate.ColProject;
import ices.fashion.service.dto.collaborate.ColProjectDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ColProjectMapper extends BaseMapper<ColProject> {
    List<ColProjectDto> selectProjectbyMemberId(@Param("mid")Integer mid);

    List<ColProjectDto> selectProjectbyId(@Param("id")Integer id);

    Integer insertProject(ColProject colProject);

    Integer deleteProject(int pid);
}
