package ices.fashion.controller;

import ices.fashion.constant.ApiResult;
import ices.fashion.constant.ResultMessage;
import ices.fashion.entity.AdminUser;
import ices.fashion.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("get-admin-user-info")
    public ApiResult<Map<String, Object>> getAdminUserInfo(@RequestParam("phone") String phone) {
        Map<String, Object> resultMap = new HashMap<>(2);
        AdminUser adminUserDto = adminService.selectAdminUserByPhone(phone);
        if (adminUserDto != null) {
            // 有该用户
            resultMap.put("isAdmin", true);
            resultMap.put("adminUser", adminUserDto);
        } else {
            resultMap.put("isAdmin", false);
        }
        return new ApiResult<>(ResultMessage.RESULT_SUCCESS_1, resultMap);
    }

}
