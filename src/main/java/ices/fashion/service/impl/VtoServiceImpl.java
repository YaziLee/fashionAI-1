package ices.fashion.service.impl;

import com.qiniu.storage.DownloadUrl;
import com.qiniu.util.Auth;
import ices.fashion.constant.QiniuCloudConst;
import ices.fashion.service.VtoService;
import ices.fashion.util.FileUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;


@Service
public class VtoServiceImpl implements VtoService {


    @Override
    public void virtualTryOn() throws UnsupportedEncodingException {

        String fileName = "20211018_1634548580000_MDA0NzY1OTk5OV8zXzFfMS5wbmc=.png";
        String encodeFileName = URLEncoder.encode(fileName, "utf-8").replace("+", "%20");
        String finalUrl = String.format("%s/%s", QiniuCloudConst.DOMAIN_BUCKET, encodeFileName);
        System.out.println(finalUrl);
        FileUtil.download(finalUrl);
        /*
        todo
        补充删除图片逻辑
        同时本地保存的图片按照前端传来的照片名称定
         */
    }



}
