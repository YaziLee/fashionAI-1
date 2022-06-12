package ices.fashion.service;

import ices.fashion.entity.AdminUser;

public interface AdminService {
    /*
    管理员端相关
     */
    // 根据手机号码查询管理员
    AdminUser selectAdminUserByPhone(String phone);
}
