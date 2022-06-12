package ices.fashion.mapper.space;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ices.fashion.entity.space.TComment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<TComment> {
}
