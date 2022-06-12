package ices.fashion.service.space.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TComment;
import ices.fashion.mapper.space.CommentMapper;
import ices.fashion.service.space.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ApiResult saveOneComment(TComment comment) {
        if(commentMapper.insert(comment) != 1) {
            return new ApiResult(800, "数据库更新失败");
        }
        return new ApiResult(200, "success");
    }
}
