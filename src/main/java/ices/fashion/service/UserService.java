package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.User;

public interface UserService {
    public ApiResult<User> getUserInfo();
}
