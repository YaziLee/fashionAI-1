package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.TComment;

public interface CommentService {
    public ApiResult saveOneComment(TComment comment);
}
