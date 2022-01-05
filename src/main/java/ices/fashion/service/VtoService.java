package ices.fashion.service;

import ices.fashion.constant.ApiResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface VtoService {

    public void virtualTryOn(String clothFileName, String modelFileName) throws IOException;

    // 上传衣服图像
    ApiResult uploadCloth(MultipartFile clothFile);

    // 上传人物图像
    ApiResult uploadImage(MultipartFile imageFile);

    // 进行衣服试穿
    String tryOn();
}
