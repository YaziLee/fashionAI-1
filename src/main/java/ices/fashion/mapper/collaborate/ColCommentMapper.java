package ices.fashion.mapper.collaborate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.collaborate.ColComment;
import ices.fashion.entity.collaborate.ColCommentQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface ColCommentMapper extends BaseMapper<ColCommentQuery> {
    List<ColCommentQuery> selectComment();
}
