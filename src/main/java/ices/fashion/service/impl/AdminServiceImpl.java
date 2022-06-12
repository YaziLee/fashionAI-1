package ices.fashion.service.impl;

import ices.fashion.entity.AdminUser;
import ices.fashion.mapper.AdminUserMapper;
import ices.fashion.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public AdminUser selectAdminUserByPhone(String phone) {
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        List<AdminUser> adminUserList = adminUserMapper.selectByMap(map);
        if (adminUserList.size() > 0)
                return adminUserList.get(0);
        else return null;
    }
}
