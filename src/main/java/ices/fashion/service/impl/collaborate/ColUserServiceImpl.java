package ices.fashion.service.impl.collaborate;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ices.fashion.entity.collaborate.ColUser;
import ices.fashion.mapper.collaborate.ColUserMapper;
import ices.fashion.service.ColUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColUserServiceImpl implements ColUserService {
    @Autowired
    ColUserMapper colUserMapper;

    @Override
    public int login(String phone,String userName){
        int id = getUserIDbyPhone(phone);
        if(id== -1){
            //说明该用户没有在collaborate的用户表中登记
            ColUser colUser = new ColUser();
            colUser.setPhone(phone);
            colUser.setUsername(userName);
            int res = colUserMapper.insert(colUser);
            return colUser.getId();
        }
        else
            return id;
    }

    private int getUserIDbyPhone(String phone){
        QueryWrapper<ColUser> colUserQueryWrapper = new QueryWrapper<>();
        colUserQueryWrapper.eq("phone",phone);
        List<ColUser> colUserList = colUserMapper.selectList(colUserQueryWrapper);
        if(colUserList.isEmpty())
            return -1;
        else
            return colUserList.get(0).getId();
    }
}
