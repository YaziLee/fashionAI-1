package ices.fashion.service.impl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import ices.fashion.service.UploadTokenService;
import org.springframework.stereotype.Service;

@Service
public class UploadTokenImpl implements UploadTokenService {
    @Override
    public String getUploadToken(boolean forever){
        String ACCESS_KEY = "afg3FFJacTTtaCxYJgG3fCYoE0iPCWrilEBnaE-A";
        String SECRET_KEY = "V7ulJSTaF5q2j0DV1YfDWLzMHHUcmfPcIN-lGenE";
        String BUCKET = "shoplook-img";

        Auth auth = Auth.create(ACCESS_KEY,SECRET_KEY);
        long expireSeconds = 7200; //上传凭证的有效时间
        int days = 1; //文建过期天数
        StringMap putPolicy = new StringMap();
        if(!forever)
            putPolicy.put("deleteAfterDays",days);
        String token= auth.uploadToken(BUCKET,null,expireSeconds,putPolicy);
        return token;
    }
}
