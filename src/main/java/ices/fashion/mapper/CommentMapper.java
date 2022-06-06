package ices.fashion.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.TComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<TComment> {
}
