package ices.fashion.service.impl;

import ices.fashion.constant.ApiResult;
import ices.fashion.entity.User;
import ices.fashion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ApiResult<User> getUserInfo() {

//        Object o = redisTemplate.opsForHash().get("4048b7498471477eab37914a866ea29720220514201001356", Object.class);
//        System.out.println(o.toString());
        System.out.println(redisTemplate.hasKey("4048b7498471477eab37914a866ea29720220514201001356"));

        User user = new User();
        ApiResult<User> res = new ApiResult(200, "success");
        res.setData(user);
        return res;
    }
}
