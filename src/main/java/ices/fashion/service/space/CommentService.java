package ices.fashion.service.space;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.space.TComment;

public interface CommentService {
    public ApiResult saveOneComment(TComment comment);
}
